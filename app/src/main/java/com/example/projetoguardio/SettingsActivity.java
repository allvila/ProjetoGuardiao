package com.example.projetoguardio;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private EditText editTel;
    private RadioGroup groupSens, groupTempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        editTel = findViewById(R.id.editTelefoneResponsavel);
        groupSens = findViewById(R.id.groupSensibilidade);
        groupTempo = findViewById(R.id.groupTempo);
        Button btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(v -> {
            if (editTel.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite um telefone!", Toast.LENGTH_SHORT).show();
                return;
            }
            salvar();
        });
    }

    private void salvar() {
        SharedPreferences.Editor editor = getSharedPreferences("ConfigGuardiao", MODE_PRIVATE).edit();
        editor.putString("tel_resp", editTel.getText().toString());

        int idS = groupSens.getCheckedRadioButtonId();
        if (idS == R.id.rbAlta) editor.putString("sensibilidade_sensor", "15");
        else if (idS == R.id.rbBaixa) editor.putString("sensibilidade_sensor", "35");
        else editor.putString("sensibilidade_sensor", "25");

        int idT = groupTempo.getCheckedRadioButtonId();
        if (idT == R.id.rb9s) editor.putString("tempo_alerta", "9");
        else if (idT == R.id.rb15s) editor.putString("tempo_alerta", "15");
        else editor.putString("tempo_alerta", "10");

        editor.apply();
        Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}