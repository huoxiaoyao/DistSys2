package ch.ethz.inf.vs.vs_fabianu_webservices.Server;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import skeleton.SimpleHttpClient;

/**
 * Created by Fabian_admin on 19.10.2015.
 */
public class EntityHttpClient implements SimpleHttpClient{
    @Override
    public String execute(Object request) {

        HttpClient cl = new DefaultHttpClient();
        HttpPost post = (HttpPost) request;

        try {
            HttpEntity response = cl.execute(post).getEntity();
            if (response != null) {
                String ret = EntityUtils.toString(response);
                return ret;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
