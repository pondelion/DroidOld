package com.example.user.wifiawaretest;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.wifi.aware.AttachCallback;
import android.net.wifi.aware.DiscoverySessionCallback;
import android.net.wifi.aware.PeerHandle;
import android.net.wifi.aware.PublishConfig;
import android.net.wifi.aware.PublishDiscoverySession;
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.aware.WifiAwareSession;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    private WifiAwareSession mAwareSession;
    private Handler mHandler = new Handler();
    public static final String AWARE_SERVICE_NAME = "Aware Service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_WIFI_AWARE)) {
            Toast.makeText(this, "Wi-Fi Aware not supoortted", Toast.LENGTH_SHORT).show();
            return;
        }

        WifiAwareManager was = (WifiAwareManager)getSystemService(WifiAwareManager.class);

        was.attach(new MyAttachCallback(), mHandler);
    }

    class MyAttachCallback extends AttachCallback {
        @Override
        public void onAttachFailed() {
            Toast.makeText(MainActivity.this, "onAttachFailed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAttached(WifiAwareSession session) {
            Toast.makeText(MainActivity.this, "onAttach", Toast.LENGTH_SHORT).show();

            MainActivity.this.mAwareSession = session;
            PublishConfig config = new PublishConfig.Builder()
                    .setServiceName(AWARE_SERVICE_NAME)
                    .build();
            MainActivity.this.mAwareSession.publish(config, new DiscoverySessionCallback() {
                @Override
                public void onPublishStarted(PublishDiscoverySession session) {
                    Toast.makeText(MainActivity.this, "onPublishStarted", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onMessageReceived(PeerHandle peerHandle, byte[] message) {
                    Toast.makeText(MainActivity.this, "onMessageReceived : " + message.toString(), Toast.LENGTH_SHORT).show();
                }
            }, null);
        }
    }
}
