package com.example.alarmmanagersample;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class GudonReceiver extends BroadcastReceiver {

	static final String TAG = "AlarmManagerSample";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String strTime = new Date().toLocaleString();
		Bundle bundle = intent.getExtras();
		String msg = bundle.getString("PARAM");
		
		Toast.makeText(context, String.format("%s - PARAM=%s", strTime, msg), Toast.LENGTH_LONG).show();
		
	}

}
