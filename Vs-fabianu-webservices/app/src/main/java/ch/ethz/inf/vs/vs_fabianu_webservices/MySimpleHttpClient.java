package ch.ethz.inf.vs.vs_fabianu_webservices;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import skeleton.HttpRawRequest;
import skeleton.SimpleHttpClient;

/**
 * Created by Caroline on 10/13/15.
 */
public class MySimpleHttpClient implements SimpleHttpClient {

    MyHttpRawRequest httpRequest = new MyHttpRawRequest();

    Socket MyClient;

    public MySimpleHttpClient(){
        try {
            MyClient = new Socket(httpRequest.getHost(), httpRequest.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String execute(Object request){

        //take in an http request
        //return http response, executed

        request = httpRequest.generateRequest();

        DataInputStream input;
        try {
            input = new DataInputStream(MyClient.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataOutputStream output;
        try {
            output = new DataOutputStream(MyClient.getOutputStream());
        }
        catch (IOException e) {
            System.out.println(e);
        }

        return "hello";
    }
}
