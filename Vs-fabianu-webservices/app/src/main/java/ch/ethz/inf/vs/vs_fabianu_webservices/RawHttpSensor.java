package ch.ethz.inf.vs.vs_fabianu_webservices;

import android.util.Log;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import skeleton.RemoteServerConfiguration;
import skeleton.sensor.AbstractSensor;

/**
 * Created by Fabian_admin on 13.10.2015.
 */
public class RawHttpSensor extends AbstractSensor {

    private final String urlStr = "http://vslab.inf.ethz.ch:8081/sunspots/Spot1/sensors/temperature";

    public RawHttpSensor() {
        super();
    }

    @Override
    protected void setHttpClient() {
        httpClient = new RawHttpClient();
    }

    @Override
    public double parseResponse(String response) {

        HtmlParser responseParser = new HtmlParser();
        return responseParser.parseResponse(response);
    }

    @Override
    public void getTemperature() throws NullPointerException {
        //init request
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

            HttpRawRequestImpl myRequest = new HttpRawRequestImpl(host, port, path);

            AsyncWorker w = new AsyncWorker();
            w.execute(myRequest);
        }
    }
}
