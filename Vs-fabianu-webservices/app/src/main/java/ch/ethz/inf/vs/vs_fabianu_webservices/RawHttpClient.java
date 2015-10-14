package ch.ethz.inf.vs.vs_fabianu_webservices;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import skeleton.SimpleHttpClient;

/**
 * Created by Caroline on 10/13/15.
 */
public class RawHttpClient implements SimpleHttpClient {

    HttpRawRequestImpl httpRequest;

    public RawHttpClient(){

    }

    public String execute(Object request){
        //take in an http request
        //return http response, executed
        httpRequest = (HttpRawRequestImpl)request;

        Socket socket = null;
        String re = "";
        try {
            socket = new Socket(httpRequest.getHost(), httpRequest.getPort());
            // create input and output stream/readers
            if (socket != null) {
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream());
                String messageOut = httpRequest.generateRequest();

                output.print(messageOut);
                output.flush();

                String lastReceived;
                do {
                    try {
                        lastReceived = input.readLine();
                        re += lastReceived;
                    } catch (IOException e) {
                        re = e.getLocalizedMessage();
                        lastReceived = null;
                    }
                } while (lastReceived != null);

                socket.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return re;
    }
}
