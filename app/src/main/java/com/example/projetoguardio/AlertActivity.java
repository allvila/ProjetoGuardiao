package com.example.projetoguardio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class AlertActivity extends AppCompatActivity {

    private TextView txtTemporizador;
    private CountDownTimer timer;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        txtTemporizador = findViewById(R.id.txtTemporizador);
        AppCompatButton btnCancelar = findViewById(R.id.btnCancelarAlerta);

        // 1. INICIAR SOM DA SIRENE
        tocarSirene();

        // 2. LER O TEMPO SALVO (Sincronizado com SettingsActivity)
        SharedPreferences prefs = getSharedPreferences("ConfigGuardiao", MODE_PRIVATE);
        // Pegamos como String e convertemos para número
        String tempoSalvoStr = prefs.getString("tempo_alerta", "10");
        int segundosSalvos = Integer.parseInt(tempoSalvoStr);

        // 3. INICIAR CONTAGEM REGRESSIVA REAL
        timer = new CountDownTimer(segundosSalvos * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Atualiza o número grande na tela (15, 14, 13...)
                txtTemporizador.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                txtTemporizador.setText("0");
                pararSomETimer();

                // Vai para a tela de Alerta Enviado (onde o SMS é processado)
                Intent intent = new Intent(AlertActivity.this, AlertSentActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();

        // 4. BOTÃO CANCELAR (Para tudo e volta para a tela inicial)
        btnCancelar.setOnClickListener(v -> {
            pararSomETimer();
            finish();
        });
    }

    private void tocarSirene() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.sirene);
            if (mediaPlayer != null) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void pararSomETimer() {
        if (timer != null) timer.cancel();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pararSomETimer();
    }
}