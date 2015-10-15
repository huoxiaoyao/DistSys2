package ch.ethz.inf.vs.vs_fabianu_webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

import skeleton.sensor.Sensor;
import skeleton.sensor.SensorFactory;
import skeleton.sensor.SensorListener;

public class RestActivity extends AppCompatActivity implements SensorListener{

    TextView text;
    //private final String urlStr = "http://vslab.inf.ethz.ch:8081/sunspots/Spot1/sensors/temperature";
    RawHttpSensor rawSens;
    HtmlSensor htmlSens;
    JsonSensor jSens;

    private Double endTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        text = (TextView) findViewById(R.id.textView);

        rawSens = (RawHttpSensor)SensorFactory.getInstance(SensorFactory.Type.RAW_HTTP);
        htmlSens = (HtmlSensor)SensorFactory.getInstance(SensorFactory.Type.HTML);
        jSens = (JsonSensor)SensorFactory.getInstance(SensorFactory.Type.JSON);
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
    public void onReceiveDouble(double value) {
        String print = String.format(getString(R.string.temperature_text), value);
        text.setText(print);
        unregisterAll();
    }

    @Override
    public void onReceiveString(String message) {

    }

    public void onClickRaw(View view){
        rawSens.registerListener(this);
        rawSens.getTemperature();
    }

    public void onClickHtml(View view){
        htmlSens.registerListener(this);
        htmlSens.getTemperature();
    }

    public void onClickJson(View view){
        //funktioniert nicht -> gibt imme 41°C aus
        //An error occured while executing doInBackground()
        jSens.registerListener(this);
        jSens.getTemperature();
    }

    private void unregisterAll() {
        rawSens.unregisterListener(this);
        htmlSens.unregisterListener(this);
        jSens.unregisterListener(this);
    }
}
