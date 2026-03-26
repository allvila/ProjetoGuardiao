package com.example.projetoguardio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import java.util.Locale;

public class AlertSentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_sent);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        TextView txtEnd = findViewById(R.id.txtEnderecoReal);
        Button btnLigar = findViewById(R.id.btnLigarEmergencia);
        CardView btnConfig = findViewById(R.id.btnConfigRodape);

        // Ação para voltar às configurações
        btnConfig.setOnClickListener(v -> {
            startActivity(new Intent(AlertSentActivity.this, SettingsActivity.class));
        });

        // Buscar localização real
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (loc == null) loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (loc != null) {
                try {
                    Geocoder geo = new Geocoder(this, Locale.getDefault());
                    txtEnd.setText("📍 " + geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1).get(0).getAddressLine(0));
                } catch (Exception e) {
                    txtEnd.setText("Localização obtida via GPS.");
                }
            }
        }

        // Ligar para o SAMU
        btnLigar.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:192"))));
    }
}