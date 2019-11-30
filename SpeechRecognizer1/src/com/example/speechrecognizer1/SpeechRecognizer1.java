package com.example.speechrecognizer1;

import java.util.ArrayList;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Menu;
import android.widget.Toast;

public class SpeechRecognizer1 extends Activity {

	SpeechRecognizer mSpeechRec;
	SampleRecognizerListener mRecogListener = new SampleRecognizerListener();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speech_recognizer1);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSpeechRec = SpeechRecognizer.createSpeechRecognizer(this);
		mSpeechRec.setRecognitionListener(mRecogListener);
		mSpeechRec.startListening(RecognizerIntent.getVoiceDetailsIntent(getApplicationContext()));
	
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mSpeechRec.destroy();
	}
	
	public class SampleRecognizerListener implements RecognitionListener {

		@Override
		public void onBeginningOfSpeech() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onBufferReceived(byte[] buffer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onEndOfSpeech() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(int error) {
			// TODO Auto-generated method stub
			mSpeechRec.setRecognitionListener(mRecogListener);
			mSpeechRec.startListening(RecognizerIntent.getVoiceDetailsIntent(getApplicationContext()));
		}

		@Override
		public void onEvent(int eventType, Bundle params) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPartialResults(Bundle partialResults) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onReadyForSpeech(Bundle params) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onResults(Bundle results) {
			// TODO Auto-generated method stub
			ArrayList<String> resultWordList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			
			String resultWord = resultWordList.get(0);
			Toast.makeText(getApplicationContext(), resultWord, Toast.LENGTH_SHORT).show();
			
			
			if ("ダッフィー".equals(resultWord)) {
				ToneGenerator toneGenerator = new ToneGenerator(
						AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);
				toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
				((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(1000);
			}
			
			mSpeechRec.setRecognitionListener(mRecogListener);
			mSpeechRec.startListening(RecognizerIntent.getVoiceDetailsIntent(getApplicationContext()));
			
		}

		@Override
		public void onRmsChanged(float rmsdB) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
