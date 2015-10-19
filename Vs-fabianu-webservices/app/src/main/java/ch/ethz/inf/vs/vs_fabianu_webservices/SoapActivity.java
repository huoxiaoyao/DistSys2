/**
 * Created by Fabian_admin on 19.10.2015.
 */
package ch.ethz.inf.vs.vs_fabianu_webservices;

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.TextView;

        import skeleton.sensor.Sensor;
        import skeleton.sensor.SensorFactory;
        import skeleton.sensor.SensorListener;



public class SoapActivity extends AppCompatActivity implements SensorListener{

    Sensor xmlSensor;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap);
        text = (TextView)findViewById(R.id.soap_text);

        xmlSensor= SensorFactory.getInstance(SensorFactory.Type.XML);

        xmlSensor.registerListener(this);
        xmlSensor.getTemperature();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_soap, menu);
        return false;
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
    }

    @Override
    public void onReceiveString(String message) {

    }
}
