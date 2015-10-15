package ch.ethz.inf.vs.a2.vs_fabianu_sensorserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    private TextView portView;
    private TextView ipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
        ipView = (TextView)findViewById(R.id.IPField);
        portView = (TextView)findViewById(R.id.portField);

        //browse network interfaces
        Enumeration<NetworkInterface> interfaces;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            interfaces = Collections.emptyEnumeration();
            //e.printStackTrace();
        }

        //test
        //String interfaceNames = "";
        while(interfaces.hasMoreElements()) {
            NetworkInterface i = interfaces.nextElement();
            ipView.append(i.getDisplayName() + '\n');
        }
    }


}
