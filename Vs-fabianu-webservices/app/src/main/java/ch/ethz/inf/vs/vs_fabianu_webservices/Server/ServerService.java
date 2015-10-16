package ch.ethz.inf.vs.vs_fabianu_webservices.Server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        int ret = super.onStartCommand(intent, flags, startId);
        port = intent.getIntExtra("PORT", 1025);
        //all client connections will be handled in this thread pool
        clientPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        serverTask = new ServerTask();
        serverThread = new Thread(serverTask);
        serverThread.start();

        return ret;
    }

    private class ServerTask implements Runnable {
        public ServerSocket serverSocket = null;
        private volatile boolean running = true;
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(port);
                while (running) {
                    //can be interrupted by calling serverSocket.close()
                    Socket clientSocket = serverSocket.accept();
                    clientPool.submit(new ClientTask(clientSocket));
                }
            } catch (IOException e) {
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

        public ClientTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            Log.i("socket" ,"CONNECTED!!");

            try {
                clientSocket.close();
            } catch (IOException e) {
                Log.i("socket", "problem closing a client socket");
            }
        }
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

        clientPool.shutdown();
        try {
            clientPool.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.i("socket", "taking some time closing all the client sockets");
        }
        super.onDestroy();
    }
}
