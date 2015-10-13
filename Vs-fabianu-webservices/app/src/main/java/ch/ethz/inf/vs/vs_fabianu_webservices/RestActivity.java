package ch.ethz.inf.vs.vs_fabianu_webservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

public class RestActivity extends AppCompatActivity {

    TextView text;
    private final String urlStr = "http://vslab.inf.ethz.ch:8081/sunspots/Spot1/sensors/temperature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);
        text = (TextView)findViewById(R.id.textView);

        //create HTTP Client and request, and execute
        URI uri = null;
        try {
            uri = new URI(urlStr);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if(uri != null) {
            String host = uri.getHost();
            String path = uri.getRawPath();
            int port = uri.getPort();

            RawHttpClient myClient = new RawHttpClient();

            HttpRawRequestImpl myRequest = new HttpRawRequestImpl(host, port, path);
            String returnMessage = myClient.execute(myRequest);
            text.setText(returnMessage);
        }
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
}
