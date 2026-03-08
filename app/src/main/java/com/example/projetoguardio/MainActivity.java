package com.example.projetoguardio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Carrega o seu design da tela principal limpo, sem aquele código problemático
        setContentView(R.layout.activity_main);

        // 1. Encontra o seu botão gigante pelo ID
        Button btnSos = findViewById(R.id.btn_sos);

        // 2. Cria a ação de clique do botão
        btnSos.setOnClickListener(v -> {
            // Quando clicar, cria a "ponte" para abrir a tela de Alerta (o cronômetro)
            Intent intent = new Intent(MainActivity.this, AlertActivity.class);
            startActivity(intent);
        });
    }
}