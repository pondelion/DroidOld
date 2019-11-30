package com.example.testaccelerometer;

import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TestAccelelerometer extends Activity implements SensorEventListener {

	private ScrollView scView;
	private TextView txtView;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private float[] nValues = new float[3];
	private float[] oValues = new float[3];
	private TickHandler tickHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		
		txtView = new TextView(this);
		txtView.setText("");
		txtView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(txtView);
		
		mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> list = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
		if(list.size() > 0) {
			mAccelerometer = list.get(0);
		}
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_accelelerometer, menu);
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(mAccelerometer != null) {
			mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		}
		
		tickHandler = new TickHandler();
		tickHandler.sleep(0);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mSensorManager.unregisterListener(this);
		tickHandler = null;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
			return;
			
		}
		
		oValues[0] = nValues[0];
		oValues[1] = nValues[1];
		oValues[2] = nValues[2];
		nValues[0] = event.values[0];
		nValues[1] = event.values[1];
		nValues[2] = event.values[2];
	}
	
	public class TickHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			StringBuffer bufStr = new StringBuffer();
			
			bufStr.append("êV value[0]: meams Xé≤" + nValues[0] + "\n");
			bufStr.append("êV value[1]: meams Yé≤" + nValues[1] + "\n");
			bufStr.append("êV value[2]: meams Zé≤" + nValues[2] + "\n");
			bufStr.append("=========================\n");
			bufStr.append("ãå value[0]: meams Xé≤" + oValues[0] + "\n");
			bufStr.append("ãå value[1]: meams Yé≤" + oValues[1] + "\n");
			bufStr.append("ãå value[2]: meams Zé≤" + oValues[2] + "\n");
			
			txtView.setText(bufStr.toString());
			if(tickHandler != null) tickHandler.sleep(2000);
		}
		
		public void sleep(long delayMills) {
			removeMessages(0);
			
			sendMessageDelayed(obtainMessage(0), delayMills);
		}
	}

}
