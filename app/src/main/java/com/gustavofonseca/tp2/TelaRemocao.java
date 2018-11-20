package com.gustavofonseca.tp2;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class TelaRemocao extends AppCompatActivity implements RemoveNo{

    private SensorManager sensorManager;
    private No mNo;
    private int pos;
    private RemocaoSensor remocaoSensor;
    private Intent devolve;
    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_listitem);
        textview = findViewById(R.id.cell_name);
        recebeIntent();
        textview.setText(mNo.getFilhos().get(pos).getNome());
        gerarSensor();
    }

    public void recebeIntent()
    {
        if(getIntent().hasExtra("posicao") && getIntent().hasExtra("No")) {
            pos = getIntent().getIntExtra("posicao", 0);
            mNo = (No) (getIntent().getSerializableExtra("No"));
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        }
    }

    public void gerarSensor()
    {
        remocaoSensor = new RemocaoSensor(sensorManager,this);
    }

    public void AtualizaIntent() {
        Log.d("Paginas", "Destruida");
        devolve = new Intent();
        devolve.putExtra("posicao",pos);
        devolve.putExtra("No", mNo);
        setResult(RESULT_OK,devolve);
    }

    @Override
    public void removeNo()
    {
        mNo.removeFilhos(pos);
        AtualizaIntent();
        finish();
    }


}
