package com.example.user.wifiawaresubscribertest;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.wifi.aware.AttachCallback;
import android.net.wifi.aware.DiscoverySessionCallback;
import android.net.wifi.aware.PeerHandle;
import android.net.wifi.aware.PublishConfig;
import android.net.wifi.aware.PublishDiscoverySession;
import android.net.wifi.aware.SubscribeConfig;
import android.net.wifi.aware.SubscribeDiscoverySession;
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.aware.WifiAwareSession;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

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

            SubscribeConfig config = new SubscribeConfig.Builder()
                    .setServiceName(AWARE_SERVICE_NAME)
                    .build();

            mAwareSession.subscribe(config, new DiscoverySessionCallback() {

                private PeerHandle mPeerHandle = null;
                @Override
                public void onSubscribeStarted(SubscribeDiscoverySession session) {
                    if (mPeerHandle != null) {
                        int messageId = 1;
                        session.sendMessage(mPeerHandle, messageId, "Test Message".getBytes());
                    }
                }

                @Override
                public void onServiceDiscovered(PeerHandle peerHandle,
                                                byte[] serviceSpecificInfo, List<byte[]> matchFilter) {
                    mPeerHandle = peerHandle;
                }
            }, null);

        }
    }
}
