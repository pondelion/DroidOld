package com.example.gpstest1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class GPSTest1 extends Activity implements OnClickListener {

	private Button btnSendStart;
	private Button btnSendStop;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gpstest1);
		
		btnSendStop = (Button)findViewById(R.id.btnSendStop);
		btnSendStart = (Button)findViewById(R.id.btnSendStart);
		
		btnSendStop.setOnClickListener(this);
		btnSendStart.setOnClickListener(this);
		
	}

	@Override 
	protected void onDestroy() {
		
		Intent locationSenderService = new Intent(GPSTest1.this, LocationSenderService.class);
		stopService(locationSenderService);
		
		super.onDestroy();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view == btnSendStop) {
			Intent locationSenderService = new Intent(GPSTest1.this, LocationSenderService.class);
			stopService(locationSenderService);
			
			btnSendStop.setEnabled(false);
			btnSendStart.setEnabled(true);
		} else if (view == btnSendStart) {
			Intent locationSenderService = new Intent(GPSTest1.this, LocationSenderService.class);
			startService(locationSenderService);
			
			btnSendStop.setEnabled(true);
			btnSendStart.setEnabled(false);
			
		}
	}

}
