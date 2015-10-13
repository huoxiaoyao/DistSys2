package ch.ethz.inf.vs.vs_fabianu_webservices;

import android.hardware.SensorEvent;
import android.hardware.SensorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URI;

public class RestActivity extends AppCompatActivity implements SensorListener, HttpRawRequest {


    String urlStr = "http://vslab.inf.ethz.ch:8081/sunspots/Spot1/sensors/temperature"; // some URL
    try{
        URI uri = new URI(urlStr);
    }
    catch(java.net.URISyntaxException){
        
    }

    String host = uri.getHost( );
    String path = uri.getRawPath( );

    public String generateRequest(){
        String temp = "GET " + path + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n\r\n";

        return temp;
    }

    @Override
    public int getPort() {
        return 8081;
    }

    public String getHost(){
        return host;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {

    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }
}
