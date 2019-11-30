package com.example.locationtest2;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class LocationTest2 extends Activity implements LocationListener {

	private LocationManager lm;
	private TextView latitude, longitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_test2);
		
		latitude = (TextView)findViewById(R.id.textView2);
		longitude = (TextView)findViewById(R.id.textView4);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location_test2, menu);
		return true;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	}
	
	@Override
	public void onStop() {
		
		super.onStop();
		
		lm.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
		latitude.setText(String.valueOf(arg0.getLatitude()));
		longitude.setText(String.valueOf(arg0.getLongitude()));
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
