package ch.ethz.inf.vs.vs_fabianu_webservices.Server;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import ch.ethz.inf.vs.vs_fabianu_webservices.R;

public class ServerActivity extends AppCompatActivity {

    private InetAddress address;
    private final int port = 7077;

    private TextView portView;
    private TextView ipView;
    private ToggleButton startStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        //init
        ipView = (TextView)findViewById(R.id.IPField);
        portView = (TextView)findViewById(R.id.portField);
        startStop = (ToggleButton)findViewById(R.id.start_stop);
        startStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    startServer();
                } else {
                    stopServer();
                }
            }
        });

        address = getWiFiIP();
        ipView.setText(address.getHostAddress());
        portView.setText(String.valueOf(port));
    }

    private InetAddress getWiFiIP() {
        Enumeration<NetworkInterface> interfaces;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            interfaces = Collections.emptyEnumeration();
        }

        while(interfaces.hasMoreElements()) {
            NetworkInterface inter = interfaces.nextElement();
            if(inter.getDisplayName().equals("wlan0")) {
                List<InetAddress> addresses = Collections.list(inter.getInetAddresses());
                for(InetAddress address: addresses) {
                    if(!address.isLoopbackAddress() && InetAddressUtils.isIPv4Address(address.getHostAddress())) {
                        return address;
                    }
                }
            }
        }

        InetAddress adr = null;
        try {
            adr = InetAddress.getByName("0.0.0.0");
        } catch (UnknownHostException e) {
            adr = null;
            e.printStackTrace();
        }
        return adr;
    }

    private void startServer() {
        Intent serverIntent = new Intent(this, ServerService.class);
        serverIntent.putExtra("PORT", port);
        startService(serverIntent);
    }

    private void stopServer() {
        Intent serverIntent = new Intent(this, ServerService.class);
        stopService(serverIntent);
    }
}
