package com.example.dothis;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.dothis.database.DadosTarefas;

public class Lista extends AppCompatActivity {

    private RecyclerView lst_tarefas; //Lista
    private FloatingActionButton btn_cadastrar; //Botao
    private ConstraintLayout layoutTarefas;

    private SQLiteDatabase conexao;

    private DadosTarefas dadosTarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lst_tarefas = (RecyclerView) findViewById(R.id.lst_tarefas);
        btn_cadastrar = (FloatingActionButton) findViewById(R.id.btn_cadastrar);
        layoutTarefas = (ConstraintLayout) findViewById(R.id.layout_tarefas);

        criarConexao();
    }

    private void criarConexao(){

        try{

            dadosTarefas = new DadosTarefas(this);
            conexao = dadosTarefas.getWritableDatabase();
            Snackbar.make(layoutTarefas, R.string.msg_conexao, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.msg_ok, null).show();

        }catch (SQLException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.msg_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.msg_ok, null);
            dlg.show();

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void cadastrar(View view){

        Intent it = new Intent(Lista.this, Cadastro.class); //chamando outra activity
        startActivity(it);

    }

}
