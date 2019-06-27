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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.dothis.database.DadosTarefas;
import com.example.dothis.dominio.entidades.Tarefa;
import com.example.dothis.dominio.repositorios.TarefaRepositorio;

import java.util.List;

public class Lista extends AppCompatActivity {

    private RecyclerView lst_tarefas;
    private FloatingActionButton btn_cadastrar;
    private ConstraintLayout layoutTarefas;

    private SQLiteDatabase conexao;

    private DadosTarefas dadosTarefas;

    private TarefaRepositorio tarefaRepositorio;
    private TarefaAdapter tarefaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lst_tarefas = (RecyclerView) findViewById(R.id.lst_tarefas);
        btn_cadastrar = (FloatingActionButton) findViewById(R.id.btn_cadastrar);
        layoutTarefas = (ConstraintLayout) findViewById(R.id.layoutTarefas);

        criarConexao();

        lst_tarefas.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lst_tarefas.setLayoutManager(linearLayoutManager);

        tarefaRepositorio = new TarefaRepositorio(conexao);

        List<Tarefa> dados = tarefaRepositorio.buscarTodos();

        tarefaAdapter = new TarefaAdapter(dados);

        lst_tarefas.setAdapter(tarefaAdapter);
    }

    private void criarConexao(){

        try{

            dadosTarefas = new DadosTarefas(this);
            conexao = dadosTarefas.getWritableDatabase();
            Snackbar.make(layoutTarefas, R.string.msg_conexao, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.msg_ok, null).show();

        }catch (SQLException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.msg_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.msg_ok, null);
            dlg.show();

        }
    }

    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }*/

    public void cadastrar(View view){

        Intent it = new Intent(Lista.this, Cadastro.class);
        startActivityForResult(it, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        List<Tarefa> dados = tarefaRepositorio.buscarTodos();
        tarefaAdapter = new TarefaAdapter(dados);
        lst_tarefas.setAdapter(tarefaAdapter);

    }
}
