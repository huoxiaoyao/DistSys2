package ch.ethz.inf.vs.vs_fabianu_webservices.Server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpRequestHandlerRegistry;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Fabian_admin on 15.10.2015.
 */
public class ServerService extends Service {
    private int port;
    private ServerTask serverTask;
    private Thread serverThread;
    private ExecutorService clientPool;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        port = intent.getIntExtra("PORT", 1025);
        //all client connections will be handled in this thread pool
        clientPool = Executors.newFixedThreadPool(5);

        serverTask = new ServerTask(this);
        serverThread = new Thread(serverTask);
        serverThread.start();

        return START_REDELIVER_INTENT;
    }

    private class ServerTask implements Runnable {
        public ServerSocket serverSocket = null;
        private volatile boolean running = true;
        private Context context;

        public ServerTask(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(port);
                while (running) {
                    //can be interrupted by calling serverSocket.close()
                    Socket clientSocket = serverSocket.accept();
                    clientPool.submit(new ClientTask(clientSocket, context));;
                }
            } catch (Exception e) {
                Log.i("server", "error while processing client request");
            }
        }

        public void stop() {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    Log.i("server", "problems closing server socket");
                }
            }
            running = false;
        }
    }

    private class ClientTask implements Runnable {
        private Socket clientSocket;
        private Context context;
        private BasicHttpProcessor httpProc;
        private HttpService httpService;
        private HttpRequestHandlerRegistry registry;
        private AllRequestHandler rHandler = null;

        public ClientTask(Socket clientSocket, Context context) {
            this.context = context;
            this.clientSocket = clientSocket;
            httpProc = new BasicHttpProcessor();
            httpProc.addInterceptor(new ResponseDate());
            httpProc.addInterceptor(new ResponseServer());
            httpProc.addInterceptor(new ResponseContent());
            httpProc.addInterceptor(new ResponseConnControl());

            httpService = new HttpService(httpProc,
                    new DefaultConnectionReuseStrategy(), new DefaultHttpResponseFactory());

            registry = new HttpRequestHandlerRegistry();

            //register all the different handlers for the different sites
            HtmlGenerator generator = new HtmlGenerator();
            generator.registerSite("/", new ChangeNothing("home.html", context));
            generator.registerSite("/sensors", new HtmlChanger("list.html", context) {
                @Override
                public String generateChangedContent() {
                    return String.format(htmlContent, "Sensors", "Sensors",
                            "/sensors/accelerometer", "Accelerometer",
                            "/sensors/orientation", "Orientation Sensor");
                }
            });
            generator.registerSite("/actuators", new HtmlChanger("list.html", context) {
                @Override
                public String generateChangedContent() {
                    return String.format(htmlContent, "Actuators", "Actuators",
                            "/actuators/vibrator", "Vibrator",
                            "/actuators/ring", "Ring Tone");
                }
            });

            SensorManager sManager = (SensorManager)context.getSystemService(context.SENSOR_SERVICE);
            android.hardware.Sensor accel = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            generator.registerSite("/sensors/accelerometer", new SensorHtmlChanger(accel, "values.html", context));

            android.hardware.Sensor ori = sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            generator.registerSite("/sensors/orientation",new SensorHtmlChanger(ori, "values.html", context));

            generator.registerSite("/actuators/vibrator", new SingleValueActuatorChanger("Vibrator", "Send Vibration time in s",
                    "/actuators/vibrator", "svActuator.html", context));

            generator.registerSite("/actuators/ring", new SingleValueActuatorChanger("Play Ringtone", "Volume (between 1 and 100)",
                    "/actuators/ring", "svActuator.html", context));


            List<String> patterns = generator.urlList();

            rHandler = new AllRequestHandler(generator, context);
            for(String s : patterns) {
                registry.register(s, rHandler);
            }

            httpService.setHandlerResolver(registry);
        }

        @Override
        public void run() {
            Log.i("socket", "CONNECTED!!");

            try {
                DefaultHttpServerConnection conn = new DefaultHttpServerConnection();
                conn.bind(clientSocket, new BasicHttpParams());
                httpService.handleRequest(conn, new BasicHttpContext());
                conn.shutdown();

            } catch (Exception e) {
                Log.i("socket", "problem reading request");
            }

            try {
                clientSocket.close();
            } catch (IOException e) {
                Log.i("socket", "problem closing a client socket");
            }
        }

    }

    private String readRequest(Socket s) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String lastReceived;
        String request = "";
        do {
            try {
                lastReceived = reader.readLine();
                request += lastReceived;
            } catch (IOException ex) {
                request = ex.getLocalizedMessage();
                lastReceived = null;
                return request;
            }
        } while (lastReceived != null);
        return request;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        //serverThread will hopefully close soon after this
        serverTask.stop();
        serverThread.interrupt();

        clientPool.shutdown();
        try {
            clientPool.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.i("socket", "taking some time closing all the client sockets");
        }
        super.onDestroy();
    }
}
