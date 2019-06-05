package com.example.dothis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity {

    private Button btn_inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Button btn_inicio = (Button) findViewById(R.id.btn_inicio);

    }

    public void iniciar(View view){

        Intent it = new Intent(Inicio.this, Lista.class);
        startActivity(it);

    }
}
