package com.example.widgettest1;

import java.util.Locale;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class AppWidgetService extends Service implements OnInitListener {

	private static final String BUTTONCLICK = "com.example.widgettest1.BUTTONCLICK";
	private TextToSpeech tts;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		tts = new TextToSpeech(this, this);
		RemoteViews view = new RemoteViews(getPackageName(), R.layout.appwidget_layout);
		
		Log.v("ttstts", "onstart");
		Intent i = new Intent();
		i.setAction(BUTTONCLICK);
		PendingIntent pi = PendingIntent.getService(this,  0, i, 0);
		view.setOnClickPendingIntent(R.id.imageButton1, pi);
		
		if (BUTTONCLICK.equals(intent.getAction())) {
			onButtonClick(view);
		}
		
		AppWidgetManager manager = AppWidgetManager.getInstance(this);
		ComponentName widget = new ComponentName(
				this,
				"com.example.widgettest1.WidgetTest1");

		
		manager.updateAppWidget(widget, view);
		
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if (tts != null) {
			tts.shutdown();
		}
	}
	
	private void onButtonClick(RemoteViews view) {
		if (tts.isSpeaking()) {
			tts.stop();
		}
		
		tts.setSpeechRate(1.0f);
		tts.speak("This is test", TextToSpeech.QUEUE_FLUSH, null);
		
		//view.setImageViewResource(R.id.imageButton1, R.drawable.ic_launcher);
	}

	
	
	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		Log.v("ttstts", "onInit");
		if(status == TextToSpeech.SUCCESS) {
			Locale locale = Locale.ENGLISH;
			if (tts.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
				tts.setLanguage(locale);
				Log.v("ttstts", "success");
			} else {
				//stopSelf();
				Log.v("ttstts", "fail");
			}
		}
	}
	

}
