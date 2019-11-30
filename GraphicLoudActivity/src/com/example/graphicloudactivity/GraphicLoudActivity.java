package com.example.graphicloudactivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.WindowManager;

public class GraphicLoudActivity extends Activity {

	
	private LoudNess mLoudNess;
	private LoudSurFaceView mSurFaceView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSurFaceView = new LoudSurFaceView(this);
		
		setContentView(mSurFaceView);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graphic_loud, menu);
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mLoudNess = new LoudNess();
		mLoudNess.setOnReachedVolumeListener(
				new LoudNess.OnReachedVolumeListener() {
					public void onReachedVolume(final short volume) {
						mSurFaceView.drawCircle(volume);
					}
				});
		new Thread(mLoudNess).start();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mLoudNess.stop();
	}

}
