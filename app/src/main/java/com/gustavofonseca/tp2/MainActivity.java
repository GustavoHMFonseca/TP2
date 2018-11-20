package com.gustavofonseca.tp2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ChamaPaginas, SensorEventListener,FragmentInData.OnInputListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button_Menu);
        Inicializar();
        initRecyclerView();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                criarPasta(view);
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                criarDoc(view);
                return false;
            }
        });
    }
    Button button;
    int counter = 1;
    No mNo;
    No principal = new No("Menu Principal","",false);
    private SensorManager sensorManager;
    private Sensor acelerometro;
    float valorAtual = SensorManager.GRAVITY_EARTH,valorAnterior = SensorManager.GRAVITY_EARTH;
    int posicaoRemocao = 0;
    String dados = null;

    private void Inicializar()
    {
        mNo = principal;
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
        No no = new No("Pasta " + counter,"",false);
        mNo.addFilhos(no);
        counter = counter + 1;
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

    @Override
    public void ChamaPaginas( No mNo, int position)
    {
        Log.d("MAIN", "CHAMOU BB");
        Intent intent = new Intent(this,Paginas.class);
        intent.putExtra("contador", counter);
        intent.putExtra("No", mNo);
        intent.putExtra("posicao",position);
        startActivityForResult(intent,1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Main", "Recuperada");
        Log.d("Main", "requestCode: " + requestCode);
        if (requestCode == 1) {
            Log.d("Main", "RequestCode ta certinho");
            Log.d("Main", "resultCode: " + resultCode);
            if (resultCode == Activity.RESULT_OK) {

                Log.d("Main", "ResultCodeTbm ta certinho");
                counter = data.getIntExtra("contador",0);
                int position = data.getIntExtra("posicao", 0);
                mNo.setFilhos((No)data.getSerializableExtra("No"),position);
                initRecyclerView();
            }
        }
        else if(requestCode == 2)
        {
            Log.d("Main", "RequestCode ta certinho");
            Log.d("Main", "resultCode: " + resultCode);
            if(resultCode == Activity.RESULT_OK)
            {
                mNo = (No)data.getSerializableExtra(("No"));
                initRecyclerView();
            }
        }
    }

    @Override
    public  void removeNo(int position)
    {

        Intent intent = new Intent(this,TelaRemocao.class);
        intent.putExtra("No",mNo);
        intent.putExtra("posicao",position);
        startActivityForResult(intent,2);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void sendInput(String input)
    {
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
