package ch.ethz.inf.vs.vs_fabianu_webservices;

import android.util.Log;

import skeleton.SimpleHttpClient;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by Caroline on 10/14/15.
 */
public class LibHttpClient implements SimpleHttpClient{

    HttpGet httpGet;

    public String execute (Object request){

        if (!(request instanceof HttpGet)) return null;
        httpGet = (HttpGet) request;

        HttpClient httpClient = new DefaultHttpClient();

        HttpResponse response = null;

        try {

            response = httpClient.execute(httpGet);

        } catch (IOException e) {

            System.err.println("Something went wrong while getting simple request");
            System.err.println(e.toString());
            return null;
        }

        Log.d("debug", "Response Code : " + String.valueOf(response.getStatusLine().getStatusCode()));


        BufferedReader rd = null;
        try {
            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {


            System.err.println("Something went wrong while producing a reader");
            System.err.println(e.toString());
            return null;
        }

        StringBuffer result = new StringBuffer();
        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
                Log.d("hello", result.toString());
            }
        } catch (IOException e) {
            System.err.println("Something went wrong while reading the response");
            System.err.println(e.toString());
            return null;
        }

        return result.toString();


    }


}
