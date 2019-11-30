package com.example.alarmmanagertest;

import java.util.Locale;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.Toast;

public class TTSService extends Service implements OnInitListener {

	private TextToSpeech tts;
	private static int mElapsedTime = 0;
	
	@Override
	public void onStart(Intent intent, int startID) {
		mElapsedTime += 10;
		tts = new TextToSpeech(this, this);
		
		Toast.makeText(this, "Service onStart()",Toast.LENGTH_LONG).show();
	}
	
	private void speak() {
		if (tts.isSpeaking()) {
			tts.stop();
		}
		
		tts.setSpeechRate(1.0f);
		tts.speak(String.valueOf(mElapsedTime) + " seconds passed", TextToSpeech.QUEUE_FLUSH, null);
		//tts.speak(String.valueOf(mElapsedTime) + "•bŒo‰ß‚µ‚Ü‚µ‚½", TextToSpeech.QUEUE_FLUSH, null);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		if (status == TextToSpeech.SUCCESS) {
			Locale locale = Locale.ENGLISH;
			if (tts.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
				tts.setLanguage(locale);
				
				speak();
				
				
			}
		}
		
		stopSelf();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		if (tts != null) {
			tts.shutdown();
		}
	}

}
