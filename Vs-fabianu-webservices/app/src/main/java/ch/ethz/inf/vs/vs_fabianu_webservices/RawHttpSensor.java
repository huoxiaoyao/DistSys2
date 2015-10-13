package ch.ethz.inf.vs.vs_fabianu_webservices;

import skeleton.sensor.AbstractSensor;

/**
 * Created by Fabian_admin on 13.10.2015.
 */
public class RawHttpSensor extends AbstractSensor {

    @Override
    protected void setHttpClient() {
        httpClient = new RawHttpClient();
    }

    @Override
    public double parseResponse(String response) {
        return 0;
    }

    @Override
    public void getTemperature() throws NullPointerException {

    }
}
