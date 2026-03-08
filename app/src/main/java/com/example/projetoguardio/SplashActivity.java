package com.example.projetoguardio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // Mostra a tela de carregamento

        // Espera 3 segundos (3000 milissegundos) e pula de tela
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Cria a "ponte" da SplashActivity para a MainActivity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

            // Fecha a tela de carregamento
            finish();
        }, 3000);
    }
}