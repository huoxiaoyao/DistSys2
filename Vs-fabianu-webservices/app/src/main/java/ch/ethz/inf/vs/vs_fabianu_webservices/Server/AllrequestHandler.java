package ch.ethz.inf.vs.vs_fabianu_webservices.Server;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.RequestLine;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Fabian_admin on 18.10.2015.
 */
public class AllRequestHandler implements HttpRequestHandler{

    private final int alarmPlayTime = 5000;
    private HtmlGenerator generator;
    private Vibrator vib = null;
    private Ringtone ring;
    private AudioManager aManager;
    private int firstVolume = -1;

    public AllRequestHandler(HtmlGenerator g, Context c) {
        generator = g;

        //for bigger Projects, move this into other classes
        vib = (Vibrator)c.getSystemService(Context.VIBRATOR_SERVICE);
        Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        ring = RingtoneManager.getRingtone(c, alert);
        aManager = (AudioManager)c.getSystemService(c.AUDIO_SERVICE);
    }

    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        final RequestLine l = httpRequest.getRequestLine();
        if(l.getMethod().equalsIgnoreCase("GET") || l.getMethod().equalsIgnoreCase("POST")) {
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

        }

        if (l.getMethod().equalsIgnoreCase("POST")) {
            //handle post requests here
            //response is already set, only have to do the action
            HttpEntity ent = ((BasicHttpEntityEnclosingRequest)httpRequest).getEntity();
            BufferedReader entBuf = new BufferedReader(new InputStreamReader(ent.getContent()));
            String rec = entBuf.readLine();
            int value = Integer.parseInt(rec.substring(6));

            if(l.getUri().equalsIgnoreCase("/actuators/vibrator")) {
                Log.i("VIBRATE", rec);
                vib.vibrate(value * 1000);
            } else if (l.getUri().equalsIgnoreCase("/actuators/ring")) {
                Log.e("RING", rec);
                if(firstVolume == -1) {
                    firstVolume = aManager.getStreamVolume(AudioManager.STREAM_RING);
                }

                int maxVolume = aManager.getStreamMaxVolume(AudioManager.STREAM_RING);
                int vol = (int)((double)maxVolume * (double)value / (double)100);
                aManager.setStreamVolume(AudioManager.STREAM_RING,
                        vol,
                        aManager.FLAG_ALLOW_RINGER_MODES | aManager.FLAG_PLAY_SOUND);
                ring.play();
                while (ring.isPlaying());
                aManager.setStreamVolume(AudioManager.STREAM_RING,
                        firstVolume,
                        aManager.FLAG_ALLOW_RINGER_MODES | aManager.FLAG_PLAY_SOUND);

            }
        }
    }
}
