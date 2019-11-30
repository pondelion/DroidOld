package com.ollyolly.cameratest1;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CameraTest1 extends Activity implements OnClickListener {

	private Handler handler = new Handler();
	Button btnTakePicture;
	CameraView cameraView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.activity_camera_test1);
		
		LinearLayout layout = (LinearLayout)findViewById(R.id.layouto);
		if(layout != null) {
			
		
		cameraView = new CameraView(this, handler);
		//cameraView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		layout.addView(cameraView);
		
		} else {
			Toast.makeText(this, "layout is null", Toast.LENGTH_LONG).show();
		}
		
		btnTakePicture = (Button)findViewById(R.id.button1);
		btnTakePicture.setOnClickListener(this);
		//this.setContentView(new CameraView(this, handler));
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btnTakePicture) {
			cameraView.takePicture();
		}
	}

	
}
