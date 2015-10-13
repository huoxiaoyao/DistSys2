package ch.ethz.inf.vs.vs_fabianu_webservices;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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

    MyHttpRawRequest httpRequest;

    public RawHttpClient(){

    }

    public String execute(Object request){
        //take in an http request
        //return http response, executed
        httpRequest = (MyHttpRawRequest)request;
        String returnMessage = "";

        AsyncTask<MyHttpRawRequest, Void, String> myTask = new AsyncTask<MyHttpRawRequest, Void, String>() {
            @Override
            protected String doInBackground(MyHttpRawRequest... params) {
                //create socket
                Socket socket = null;
                String re = "";
                MyHttpRawRequest req = params[0];
                try {
                    socket = new Socket(req.getHost(), req.getPort());
                    // create input and output stream/readers
                    if (socket != null) {
                        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter output = new PrintWriter(socket.getOutputStream());
                        String messageOut = req.generateRequest();

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
                        return re;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(httpRequest);

        try {
            returnMessage = myTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return returnMessage;
    }
}
