package com.gustavofonseca.tp2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.hardware.SensorManager;

public class RemocaoSensor implements SensorEventListener {

    SensorManager sensorManager;
    Sensor acelerometro;
    float valorAnterior = 0,valorAtual = 0;
    RemoveNo removeNo;


    public RemocaoSensor( SensorManager sensorManager,RemoveNo removeNo)
    {
        this.sensorManager = sensorManager;
        this.removeNo = removeNo;

        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(RemocaoSensor.this,acelerometro, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];


        valorAnterior = valorAtual;
        valorAtual = x;
        float  aceleracao = (valorAtual - valorAnterior);

        if(aceleracao > 4 || aceleracao < -4)
        {
            sensorManager.unregisterListener(this);
            removeNo.removeNo();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
