package com.example.recordvoice;

import java.io.File;


import java.io.IOException;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class RecordVoice extends Activity {

	private MediaRecorder mRecorder;
	
	private TextView txtView;
	private boolean isRecording = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_voice);
		txtView = (TextView)findViewById(R.id.textView1);
	}
	
	public void onClickStartButton(View view) {
		if(isRecording) return;
		
		txtView.setText("˜^‰¹’†....");
		
		File dir = Environment.getExternalStorageDirectory();
		File appDir = new File(dir, "RecordVoice");
		if(!appDir.exists()) appDir.mkdir();
		String name = "testVoice" + ".3gp";
		String path = new File(appDir, name).getAbsolutePath();
		
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		mRecorder.setOutputFile(path);
		try {
			mRecorder.prepare();
			mRecorder.start();
			isRecording = true;
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onClickStopButton(View view) {
		if(!isRecording) return;
		
		txtView.setText("˜^‰¹‚µ‚Ü‚µ‚½");
		
		mRecorder.stop();
		mRecorder.reset();
		mRecorder.release();
		isRecording = false;
	}

	
}
