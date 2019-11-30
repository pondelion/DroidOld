package com.example.takeapicture;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.WindowManager;


public class TakeAPictureActivity extends Activity {

	private LoudNess mLoudNess;
	private CameraView mCameraView;
	
	public short sVolume;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCameraView = new CameraView(this);
		
		setContentView(mCameraView);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.take_apicture, menu);
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mLoudNess = new LoudNess();
		mLoudNess.setOnReachedVolumeListener(new LoudNess.OnReachedVolumeListener() {
			public void onReachedVolume(final short volume) {
				mCameraView.takeAPicture();
			}
			
			
		});
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mLoudNess.stop();
	}

}
