package ch.ethz.inf.vs.vs_fabianu_webservices;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import skeleton.HttpRawRequest;


/**
 * Created by Caroline on 10/13/15.
 */
public class HttpRawRequestImpl implements HttpRawRequest{

    private String host;
    private String path;
    private String temp;
    private int port;

    public HttpRawRequestImpl(String host, int port, String path){
        this.host = host;
        this.port = port;
        this.path = path;
    }

    public String generateRequest(){
        temp = "GET " + path + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n\r\n";

        return temp;

    }

    public int getPort() {
        return port;
    }

    public String getHost(){
        return host;
    }
}
