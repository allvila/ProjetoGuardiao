package com.example.projetoguardio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Importação essencial para o Java encontrar os botões do XML
import com.example.projetoguardio.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Vincula o código ao arquivo activity_login.xml
        setContentView(R.layout.activity_login);

        // 2. Esconde a barra superior para um visual mais moderno
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // 3. Mapeia os componentes do XML (Verifique se os IDs são esses mesmos)
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView txtIrCadastro = findViewById(R.id.txtIrCadastro);

        // --- LÓGICA DO BOTÃO ENTRAR ---
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aqui você pode adicionar validação de e-mail e senha no futuro
                // Por enquanto, ele apenas abre a tela do Botão de Emergência
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                // Toast é aquela mensagem rápida que aparece no pé da tela
                Toast.makeText(LoginActivity.this, "Bem-vindo ao iGuard!", Toast.LENGTH_SHORT).show();

                finish(); // Fecha o login para não voltar nele ao apertar 'Voltar'
            }
        });

        // --- LÓGICA DO LINK "CADASTRE-SE" ---
        txtIrCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a tela de Cadastro que criamos
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });
    }
}