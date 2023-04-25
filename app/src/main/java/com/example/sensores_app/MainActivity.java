package com.example.sensores_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import java.util.List;

//lib para sensores
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements SensorEventListener{

    private TextView tv1, tv2, tv3;
    private Sensor vsAcelero, vsRota, vsProx;
    private SensorManager vSM;
private RelativeLayout.LayoutParams layau
    private ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layau = setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        vSM = (SensorManager) getSystemService(SENSOR_SERVICE);
        vsAcelero = (Sensor) vSM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        vsRota = (Sensor) vSM.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        vsProx = (Sensor) vSM.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        img1 = (ImageView) findViewById(R.id.img1);

        //para listar los sensores
        //List <Sensor> listaSensores = vSM.getSensorList(Sensor.TYPE_ALL);
        //for(int i=0; i<listaSensores.size(); i++) {
        //    tv1.append(listaSensores.get(i).getName()+" \n");
        //   }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (vsAcelero != null) {
            vSM.registerListener(this, vsAcelero, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            tv1.setText("Acelerómetro no soportado");
        }
        if (vsRota != null) {
            vSM.registerListener(this, vsRota, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            tv2.setText("Sensor Vector Rotación no soportado");
        }
        if (vsProx != null) {
            vSM.registerListener(this, vsProx, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            tv3.setText("Sensor Proximidad no soportado");
        }
    }




    @Override
    public final void onAccuracyChanged(Sensor vSensor, int vPresicion) {
    }
    @Override
    public final void onSensorChanged(SensorEvent vEvento) {

       if (vEvento.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            tv1.setText("Acelerómetro X: " + vEvento.values[0] + " \n" +
                        "Acelerómetro Y: " + vEvento.values[1] + " \n" +
                        "Acelerómetro Z: " + vEvento.values[2] + " \n");
       }

       if (vEvento.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            tv2.setText("Valor Sensor Orientacion : " + vEvento.values[2] + " \n" );
            if (vEvento.values[2] > 1.5){
                img1.setBackgroundColor(Color.argb(200,50,220,20));
            }
            if (vEvento.values[2] < 0.5){
                img1.setBackgroundColor(Color.argb(200,247,10,20));
            }
            if (vEvento.values[2] >= 0 && vEvento.values[2] < 1.5){
                img1.setBackgroundColor(Color.argb(240,20,20,200));
            }
       }
       if (vEvento.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            tv3.setText("Valor Sensor Proximidad: " + vEvento.values[0] + " \n" );
       
       }

    }

    @Override
    protected void onResume() {
        super.onResume();
        vSM.registerListener(this, vsAcelero, SensorManager.SENSOR_DELAY_NORMAL);
        vSM.registerListener(this, vsRota, SensorManager.SENSOR_DELAY_NORMAL);
        vSM.registerListener(this, vsProx, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        vSM.unregisterListener(this);
    }

    protected void onStop() {
        super.onStop();
        vSM.unregisterListener(this);
    }




}