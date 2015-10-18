package ch.ethz.inf.vs.vs_fabianu_webservices.Server;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.RequestLine;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import ch.HtmlGenerator;

/**
 * Created by Fabian_admin on 18.10.2015.
 */
public class AllRequestHandler implements HttpRequestHandler{

    private HtmlGenerator generator;

    public AllRequestHandler(HtmlGenerator g) {
        generator = g;
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        final RequestLine l = httpRequest.getRequestLine();
        if(l.getMethod().equalsIgnoreCase("GET")) {
            //handle get requests here
            Log.i("request", l.getUri());
            HttpEntity entity = new EntityTemplate(new ContentProducer() {
                @Override
                public void writeTo(OutputStream outputStream) throws IOException {
                    OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
                    String resp = generator.generate(l.getUri());

                    writer.write(resp);
                    writer.flush();
                }
            });
            httpResponse.setHeader("Content-Type", "text/html");
            httpResponse.setEntity(entity);

        } else if (l.getMethod().equalsIgnoreCase("POST")) {
            //handle post requests here
        }
    }
}
