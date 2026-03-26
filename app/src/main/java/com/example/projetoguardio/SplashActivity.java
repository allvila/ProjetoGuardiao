package com.example.projetoguardio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

// Importação crucial para reconhecer o layout activity_splash
import com.example.projetoguardio.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Define o layout da Splash Screen (Imagem 1)
        setContentView(R.layout.activity_splash);

        // 2. Esconde a barra de título para o design ficar fiel à imagem
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // 3. Lógica de transição automática (Handler)
        // Espera 3000 milissegundos (3 segundos) antes de ir para o Login
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tenta abrir a LoginActivity
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);

                // Finaliza a Splash para ela não ficar na pilha de telas
                finish();
            }
        }, 3000);
    }
}