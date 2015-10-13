package ch.ethz.inf.vs.vs_fabianu_webservices;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import skeleton.HttpRawRequest;


/**
 * Created by Caroline on 10/13/15.
 */
public class MyHttpRawRequest implements HttpRawRequest{

    String urlStr = "http://vslab.inf.ethz.ch:8081/sunspots/Spot1/sensors/temperature"; // some URL

    private URI uri;
    private String host;
    private String path;
    private String temp;

    public MyHttpRawRequest(){
        try {
            uri = new URI(urlStr);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        host = uri.getHost( );
        path  = uri.getRawPath( );

        // Log.d("hello", "---------" + path + " " + host);
        //Log.d("hello", "GET " + path + " HTTP/1.1\r\n" + "Host: " + host + "\r\n" + "Connection: close\r\n\r\n");
    }

    public String generateRequest(){
        temp = "GET " + path + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n\r\n";

        return temp;

    }

    public int getPort() {
        return 8081;
    }

    public String getHost(){
        return host;
    }
}
