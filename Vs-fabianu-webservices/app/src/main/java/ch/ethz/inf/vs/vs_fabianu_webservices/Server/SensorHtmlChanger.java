package ch.ethz.inf.vs.vs_fabianu_webservices.Server;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Fabian_admin on 19.10.2015.
 */
public class SensorHtmlChanger extends HtmlChanger implements SensorEventListener {
    private Sensor sensor;
    private SensorManager sManager;
    private volatile boolean gotResult = false;
    private float[] values = null;

    public SensorHtmlChanger(Sensor s, String pathToHtml, Context context) {
        super(pathToHtml, context);
        sensor = s;
        sManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public String generateChangedContent() {
        while(!gotResult);
        String valueString = "";
        for(float f : values) {
            valueString += String.valueOf(f) + "; ";
        }
        String name = sensor.getName();
        return String.format(htmlContent, name, name, valueString);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(!gotResult) {
            gotResult = true;
            values = event.values;
            sManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
