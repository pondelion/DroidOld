package com.example.gpstest1;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class LocationSenderService extends Service implements LocationListener {

	private final String server = "http://192.168.0.4/";
	private final String path = "PHP/LocationLogger.php";
	private String username = "pondelion";
	private LocationManager lm;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		lm = (LocationManager)getSystemService(LOCATION_SERVICE);
		
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, this);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 0, this);
		
		Toast.makeText(this, "�T�[�r�X�J�n", Toast.LENGTH_LONG).show();
			
	}
	
	@Override
	public void onDestroy() {
		lm.removeUpdates(this);
		
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		String url = server 
				+ path
				+ "?username="
				+ username
				+ "&latitude="
				+ location.getLatitude()
				+ "&longitude="
				+ location.getLongitude()
				+ "&timestamp="
				+ System.currentTimeMillis();
		
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.execute(new HttpGet(url));
			Toast.makeText(getApplicationContext(), "�ʒu�𑗐M���܂���", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
