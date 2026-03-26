package com.example.projetoguardio;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        EditText editNome = findViewById(R.id.editNomeIdoso); // Garanta que esses IDs existam no XML
        EditText editTel = findViewById(R.id.editTelResponsavel);
        Button btnFinalizar = findViewById(R.id.btnFinalizarCadastro);

        btnFinalizar.setOnClickListener(v -> {
            String nome = editNome.getText().toString();
            String tel = editTel.getText().toString();

            if (!nome.isEmpty() && !tel.isEmpty()) {
                // SALVANDO OS DADOS NO CELULAR
                SharedPreferences.Editor editor = getSharedPreferences("ConfigGuardiao", MODE_PRIVATE).edit();
                editor.putString("nome_idoso", nome);
                editor.putString("tel_resp", tel);
                editor.apply();

                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                finish(); // Volta para o Login
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}