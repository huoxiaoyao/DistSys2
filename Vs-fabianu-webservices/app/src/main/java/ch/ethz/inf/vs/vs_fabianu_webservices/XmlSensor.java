package ch.ethz.inf.vs.vs_fabianu_webservices;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

import skeleton.SimpleHttpClientFactory;
import skeleton.sensor.AbstractSensor;

/**
 * Created by Fabian_admin on 19.10.2015.
 */
public class XmlSensor extends AbstractSensor {

    HttpPost postRequest;
    AsyncWorker worker;

    @Override
    protected void setHttpClient() {

        //Creating XML-Request
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Header/><S:Body><ns2:getSpot xmlns:ns2=\"http://webservices.vslecture.vs.inf.ethz.ch/\"><id>Spot3</id></ns2:getSpot></S:Body></S:Envelope>";
        StringEntity ent = null;
        try {
            ent = new StringEntity(xml, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ent.setContentType("text/xml");

        //Creating HTTP Post-Request
        String url = "http://vslab.inf.ethz.ch:8080/SunSPOTWebServices/SunSPOTWebservice";
        postRequest = new HttpPost(url);
        postRequest.addHeader("Connection", "close"); //Header added, as advised by slides
        postRequest.setEntity(ent);

        //Setting HTML client
        httpClient = SimpleHttpClientFactory.getInstance(SimpleHttpClientFactory.Type.ENTI);
    }

    @Override
    public void getTemperature() throws NullPointerException {

        worker = new AsyncWorker();
        worker.execute(postRequest);

    }

    @Override
    public double parseResponse(String response) {

        XmlParser responseParser = new XmlParser();
        return responseParser.parseResponse(response);

    }

}
