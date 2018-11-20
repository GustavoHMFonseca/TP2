package com.gustavofonseca.tp2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

public class Paginas extends AppCompatActivity implements ChamaPaginas,FragmentInData.OnInputListener {


    No mNo;
    int counter;
    int position;
    Intent devolve;
    String dados;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIncomingIntent();
        initRecyclerView();
    }




    public void initRecyclerView()
    {
        RecyclerView recyclerview = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.getApplicationContext(), mNo.getFilhos(), this);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }



    public void criarPasta(View view)
    {
        No no = new No("Pasta" + counter,"",false);
        mNo.addFilhos(no);
        counter = counter + 1;
        AtualizaIntent();
        initRecyclerView();
    }
    public void criarDoc(View view)
    {

        FragmentInData frag =  new FragmentInData();
        Bundle bundle = new Bundle();
        bundle.putString("Nome","Documento" + counter);
        frag.setArguments(bundle);
        frag.show(getSupportFragmentManager(),"frag");
    }


    public void getIncomingIntent()
    {
        if(getIntent().hasExtra("contador") && getIntent().hasExtra("No"))
        {
            counter = getIntent().getIntExtra("contador",0);
            position = getIntent().getIntExtra("posicao",0);
            mNo = (No)(getIntent().getSerializableExtra("No"));
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        }
    }

    public void AtualizaIntent() {
        Log.d("Paginas", "Destruida");
        devolve = new Intent();
        devolve.putExtra("contador", counter);
        devolve.putExtra("posicao",position);
        devolve.putExtra("No", mNo);
        setResult(RESULT_OK,devolve);
    }

    @Override
    public void ChamaPaginas(No mNo, int position) {
        Log.d("Paginas", "Recuperada");
        Intent intent = new Intent(this,Paginas.class);
        intent.putExtra("contador", counter);
        intent.putExtra("No", mNo);
        intent.putExtra("posicao",position);
        startActivityForResult(intent,1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                counter = data.getIntExtra("contador",0);
                int position = data.getIntExtra("posicao", 0);
                mNo.setFilhos((No)data.getSerializableExtra("No"),position);
                AtualizaIntent();
                initRecyclerView();
            }
        }
    }

    @Override
    public void removeNo(int position)
    {
        mNo.removeFilhos(position);
        AtualizaIntent();
    }

    @Override
    public void sendInput(String input) {
        dados = input;
        No no = new No("Documento" + counter,dados,true);
        mNo.addFilhos(no);
        counter = counter + 1;
        initRecyclerView();
    }

    @Override
    public void ExibeDado(No No)
    {
        FragmentOutData frag =  new FragmentOutData();
        Bundle bundle = new Bundle();
        bundle.putString("Nome",No.getNome());
        bundle.putString("Dados",No.getDados());
        frag.setArguments(bundle);
        frag.show(getSupportFragmentManager(),"frag");
    }
}
