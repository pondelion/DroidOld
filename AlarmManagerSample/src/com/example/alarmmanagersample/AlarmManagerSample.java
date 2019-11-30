package com.example.alarmmanagersample;

import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AlarmManagerSample extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		
		Button button;
		button = new Button(this);
		button.setText("ELAPSED_REALTIME‚Ì—á");
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Context context = AlarmManagerSample.this;
				AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
				
				Intent intent = new Intent(context, GudonReceiver.class);
				intent.putExtra("PARAM",  String.format("ELAPSED_REALTIME, %s", new Date().toLocaleString()));
				PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				long elapsedRealtime = SystemClock.elapsedRealtime();
				alarmManager.set(AlarmManager.ELAPSED_REALTIME, elapsedRealtime + 1000, pendingIntent);
			}
		});
		layout.addView(button);
		
		button = new Button(this);
		button.setText("RTC‚Ì—á");
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Context context = AlarmManagerSample.this;
				AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
				Intent intent = new Intent(context, GudonReceiver.class);
				intent.putExtra("PARAM", String.format("RTC, %s", new Date().toLocaleString()));
				PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				long currentTimeMillis = System.currentTimeMillis();
				alarmManager.set(AlarmManager.RTC, currentTimeMillis+1000, pendingIntent);
				
			}
		});
		layout.addView(button);;
		
		button = new Button(this);
		button.setText("setRepeatin‚Ì—á");
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Context context = AlarmManagerSample.this;
				AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
				
				Intent intent = new Intent(context, GudonReceiver.class);
				intent.putExtra("PARAM", String.format("setRepeating, %s",new Date().toLocaleString()));
				intent.setData(Uri.parse("http://xxx"));
				PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				long currentTimeMillis = System.currentTimeMillis();
				alarmManager.setRepeating(AlarmManager.RTC, currentTimeMillis + 1000, 5000, pendingIntent);
			}
		});
		layout.addView(button);
		
		button = new Button(this);
		button.setText("setInexactRepeating‚Ì—á");
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Context context = AlarmManagerSample.this;
				AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
				
				Intent intent = new Intent(context, GudonReceiver.class);
				intent.putExtra("PARAM", String.format("setInexactRepeating,  %s", new Date().toLocaleString()));
				intent.setData(Uri.parse("http://yyy"));
				PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				long currentTimeMillis = System.currentTimeMillis();
				alarmManager.setInexactRepeating(AlarmManager.RTC, currentTimeMillis+1000, 10000, pendingIntent);
				
			}
		});
		layout.addView(button);
	}
	
	@Override
	protected void onDestroy() {
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		Intent intent;
		PendingIntent pendingIntent;
		
		intent = new Intent(this, GudonReceiver.class);
		intent.setData(Uri.parse("http://xxx"));
		pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.cancel(pendingIntent);
		
		intent = new Intent(this, GudonReceiver.class);
		intent.setData(Uri.parse("http://yyy"));
		pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.cancel(pendingIntent);
		
		super.onDestroy();
	}


}
