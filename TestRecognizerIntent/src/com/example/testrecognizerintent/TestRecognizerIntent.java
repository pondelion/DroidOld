package com.example.testrecognizerintent;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TestRecognizerIntent extends Activity implements OnClickListener {

	private static final int REQUEST_CODE = 0;
	private Button btnStart;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		
		btnStart = new Button(this);
		btnStart.setText("äJén");
		btnStart.setOnClickListener(this);
		btnStart.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(btnStart);
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			intent.putExtra(RecognizerIntent.EXTRA_PROMPT,  "âπê∫îFéØ");
			intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
			startActivityForResult(intent, REQUEST_CODE);
		} catch(ActivityNotFoundException e) {
			Toast.makeText(TestRecognizerIntent.this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
		{
			String resStr = "";
			
			ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			
			for(int i=0; i< results.size(); i++) {
				resStr += results.get(i);
			}
			Toast.makeText(this, resStr, Toast.LENGTH_LONG);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	

}
