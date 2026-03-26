package com.example.projetoguardio;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class SensorService extends Service implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean alertaAtivo = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        criarNotificacao();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        return START_STICKY;
    }

    private void criarNotificacao() {
        String channelId = "guardiao_service";
        NotificationChannel channel = new NotificationChannel(channelId, "Guardião Ativo", NotificationManager.IMPORTANCE_LOW);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification notification = new Notification.Builder(this, channelId)
                .setContentTitle("Guardião Protegendo")
                .setContentText("O sensor de queda está ativo.")
                .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
                .build();
        startForeground(1, notification);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0], y = event.values[1], z = event.values[2];
        double gForce = Math.sqrt(x * x + y * y + z * z);
        if (gForce > 25.0 && !alertaAtivo) {
            alertaAtivo = true;
            Intent i = new Intent(this, AlertActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}