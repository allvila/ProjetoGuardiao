package com.example.projetoguardio;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class AlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert); // Apenas carrega a tela vermelha
    }
}