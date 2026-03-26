package com.example.projetoguardio;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acelerometro;
    private float sensibilidade;
    private boolean alertaAtivo = false;
    private TextView txtStatusLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        txtStatusLocal = findViewById(R.id.txtLocalizacaoStatus);
        RelativeLayout btnEmergencia = findViewById(R.id.btnEmergenciaPrincipal);
        LinearLayout btnConfig = findViewById(R.id.location_bar_main);

        // Abre configurações
        btnConfig.setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));

        // Abre contagem regressiva
        btnEmergencia.setOnClickListener(v -> dispararAlerta());

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        pedirPermissoes();
    }

    private void pedirPermissoes() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    private void dispararAlerta() {
        if (!alertaAtivo) {
            alertaAtivo = true;
            startActivity(new Intent(this, AlertActivity.class));
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double g = Math.sqrt(event.values[0]*event.values[0] + event.values[1]*event.values[1] + event.values[2]*event.values[2]);
        SharedPreferences prefs = getSharedPreferences("ConfigGuardiao", MODE_PRIVATE);
        sensibilidade = Float.parseFloat(prefs.getString("sensibilidade_sensor", "25"));

        if (g > sensibilidade && !alertaAtivo) {
            dispararAlerta();
        }
    }

    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume() {
        super.onResume();
        alertaAtivo = false; // Reset para poder disparar de novo
        if (acelerometro != null) sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);

        // Atualiza endereço na barra
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (loc != null) {
                try {
                    Geocoder geo = new Geocoder(this, Locale.getDefault());
                    txtStatusLocal.setText("📍 " + geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1).get(0).getThoroughfare());
                } catch (Exception e) { txtStatusLocal.setText("Localização Ativa"); }
            }
        }
    }

    @Override protected void onPause() { super.onPause(); sensorManager.unregisterListener(this); }
}