package net.ollyolly.stopwatch1;

import java.util.Locale;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.Toast;

public class TTSService extends Service implements OnInitListener {

	private TextToSpeech tts;
	private boolean isEnglishAvailable = false;
	private boolean isJapaneseAvailable = false;

	
	@Override
	public void onStart(Intent intent, int startID) {

		tts = new TextToSpeech(this, this);
		
		//Toast.makeText(this, "Service onStart()",Toast.LENGTH_LONG).show();
	}
	
	private void speak() {
		if (tts.isSpeaking()) {
			tts.stop();
		}
		
		tts.setSpeechRate(1.0f);
		if (this.isJapaneseAvailable) {
			tts.speak("ŠÔ‚É‚È‚è‚Ü‚µ‚½", TextToSpeech.QUEUE_FLUSH, null);
		} else if (this.isEnglishAvailable) {
			tts.speak("Time up", TextToSpeech.QUEUE_FLUSH, null);
		}
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
			
			Locale locale = Locale.JAPANESE;
			if (tts.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
				this.isJapaneseAvailable = true;
				tts.setLanguage(locale);
				speak();
			} else if (tts.isLanguageAvailable(locale = Locale.ENGLISH) >= TextToSpeech.LANG_AVAILABLE) {
				
				this.isEnglishAvailable = true;
				tts.setLanguage(locale);
				speak();
			} else {
				//“ú–{ŒêE‰pŒê—¼•û‘Î‰‚µ‚Ä‚¢‚È‚¢
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
