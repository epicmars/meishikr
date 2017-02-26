package com.meishikr.app.service;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by yinhang on 16/2/21.
 */
public class SensorService {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener listener;

    public SensorService(Context context, int type){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(type);
    }

    public void registerListener(SensorEventListener listener){
        this.listener = listener;
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void unresigerListener(){
        sensorManager.unregisterListener(listener);
    }

}
