package ch.ethz.inf.vs.vs_fabianu_webservices;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import skeleton.RemoteServerConfiguration;
import skeleton.sensor.AbstractSensor;

/**
 * Created by Fabian_admin on 13.10.2015.
 */
public class RawHttpSensor extends AbstractSensor {

    double temp = 0;
    @Override
    protected void setHttpClient() {
        httpClient = new RawHttpClient();
    }

    @Override
    public double parseResponse(String response) {

        final String regExp = "<span class=\"getterValue\">(\\S+)</span>";

        if (response != null) {
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(response);
            if (matcher.find()) {

                String findings = matcher.group(1);
                temp = Double.parseDouble(findings);
            }

        }
        return temp;
    }

    @Override
    public void getTemperature() throws NullPointerException {

    }
}
