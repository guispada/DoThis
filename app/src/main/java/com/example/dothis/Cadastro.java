package com.example.dothis;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.dothis.database.DadosTarefas;
import com.example.dothis.dominio.entidades.Tarefa;
import com.example.dothis.dominio.repositorios.TarefaRepositorio;

import java.util.Calendar;

public class Cadastro extends AppCompatActivity {

    private EditText edt_materia;
    private EditText edt_tarefa;
    private EditText edt_descricao;
    private Button   btn_entrega;
    private DatePickerDialog.OnDateSetListener setDate;

    private SQLiteDatabase conexao;
    private DadosTarefas dadosTarefas;
    private TarefaRepositorio tarefaRepositorio;
    private ConstraintLayout layout_cadastro;

    private Tarefa tare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edt_materia = (EditText) findViewById(R.id.edt_materia);
        edt_tarefa = (EditText) findViewById(R.id.edt_tarefa);
        edt_descricao = (EditText) findViewById(R.id.edt_descricao);
        btn_entrega = (Button) findViewById(R.id.btn_entrega);
        layout_cadastro = (ConstraintLayout) findViewById(R.id.layout_cadastro);

        btn_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Cadastro.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setDate,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        setDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d(TAG, "onDateSet: Date: " + i + "/" + i1 + "/" + i2);
                String date = day + "/" + month + "/" + year;
                btn_entrega.setText(date);
            }
        };

        criarConexao();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro, menu); //criando o menu_cadastro

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.act_ok:
                confirmar();
                break;

            case R.id.act_cancelar:
                voltar();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Lista.class);
        startActivity(intent);
    }

    private void criarConexao(){

        try{

            dadosTarefas = new DadosTarefas(this);
            conexao = dadosTarefas.getWritableDatabase();
            Snackbar.make(layout_cadastro, R.string.msg_conexao, Snackbar.LENGTH_SHORT)
                    .setAction(R.string.msg_ok, null).show();

            tarefaRepositorio = new TarefaRepositorio(conexao);

        }catch (SQLException ex){

            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.msg_erro);
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton(R.string.msg_ok, null);
            dlg.show();

        }
    }

    private void confirmar(){

        tare = new Tarefa();

        if(validaCampos() == false){

            try{

                tarefaRepositorio.inserir(tare);

                finish();

            }catch (SQLException ex){

                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle(R.string.msg_erro);
                dlg.setMessage(ex.getMessage());
                dlg.setNeutralButton(R.string.msg_ok, null);
                dlg.show();

            }
        }
    }

    private boolean validaCampos() {

        boolean res = false;

        String materia = edt_materia.getText().toString();
        String tarefa = edt_tarefa.getText().toString();
        String descricao = edt_descricao.getText().toString();
        String entrega = btn_entrega.getText().toString();

        tare.materia   = materia;
        tare.tarefa    = tarefa;
        tare.descricao = descricao;
        tare.entrega   = entrega;


        if (isCampoVazio(materia)) {
            edt_materia.requestFocus();
            res = true;
        } else if (isCampoVazio(tarefa)) {
            edt_tarefa.requestFocus();
            res = true;
        } else if (isCampoVazio(descricao)) {
            edt_descricao.requestFocus();
            res = true;
        } else if (isCampoVazio(entrega)) {
            btn_entrega.requestFocus();
            res = true;
        }

        if (res==true) {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle(R.string.aviso);
            dlg.setMessage(R.string.msg_aviso);
            dlg.setNeutralButton(R.string.msg_ok, null);
            dlg.show();
        }
        return res;
    }

    private boolean isCampoVazio(String valor) {
        boolean ata = (TextUtils.isEmpty(valor) || valor.trim().isEmpty()); //vericando campos vazios
        return ata;
    }

    private void voltar() {
        Intent intent = new Intent(this, Lista.class);
        startActivity(intent);
    }

}
