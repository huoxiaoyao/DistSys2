package ch.ethz.inf.vs.vs_fabianu_webservices;

import android.util.Log;

import skeleton.RemoteServerConfiguration;
import skeleton.SimpleHttpClientFactory;
import skeleton.sensor.AbstractSensor;
import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Caroline on 10/14/15.
 */

public class HtmlSensor extends AbstractSensor {


    HttpGet getRequest;
    AsyncWorker worker;

    private final String urlStr = "http://vslab.inf.ethz.ch:8081/sunspots/Spot1/sensors/temperature";

    @Override
    protected void setHttpClient() {


        URI uri = null;

        try {
            uri = new URI(urlStr);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        getRequest = new HttpGet(uri);

        httpClient = SimpleHttpClientFactory.getInstance(SimpleHttpClientFactory.Type.LIB);

        Log.d("debug", "Set up LibHttpClient");


    }

    @Override
    public double parseResponse(String response) {
        Log.d("debug", "Trying to parse");

        HtmlParser responseParser = new HtmlParser();
        return responseParser.parseResponse(response);

    }

    @Override
    public void getTemperature() throws NullPointerException {

        worker = new AsyncWorker();
        worker.execute(getRequest);

    }
}
