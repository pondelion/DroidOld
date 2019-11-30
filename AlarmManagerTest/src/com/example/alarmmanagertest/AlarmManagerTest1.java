package com.example.alarmmanagertest;

import java.util.List;



import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

public class AlarmManagerTest1 extends Activity {

	public static int REQUEST_CODE = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_manager_test1);
		
		setVolumeControlStream(AudioManager.STREAM_ALARM);
		
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(this, TTSService.class);
		PendingIntent pi = PendingIntent.getService(this, REQUEST_CODE, intent, 0);
		alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 10000, 10000, pi); //10�b�ォ��10�b���݂�TTSService���N��
	}

	@Override
	protected void onDestroy() {
		
		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);		
		Intent intent = new Intent(this, TTSService.class);
		PendingIntent pi = PendingIntent.getService(this, REQUEST_CODE, intent, 0);
		manager.cancel(pi);//AlarmManager�ɂ��T�[�r�X�J��Ԃ��N��������
		
		ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		List<RunningServiceInfo> rsiList = activityManager.getRunningServices(Integer.MAX_VALUE);
		for(RunningServiceInfo rsi : rsiList) {
			if (rsi.service.getClassName().equals("com.example.alarmmanagertest.TTSService.class")) {
				intent = new Intent(this, TTSService.class);//TTSService���N�����Ă����狭���I��
				stopService(intent);
			}
		}
		
		super.onDestroy();
	}
}
