package com.example.recognizeto;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class RecognizeTo extends Activity implements OnClickListener {

	private static final int REQUEST_CODE = 0;
	private Button btnStart;
	private Button btnSend;
	private EditText editText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		
		btnStart = new Button(this);
		btnStart.setText("äJén");
		btnStart.setOnClickListener(this);;
		btnStart.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(btnStart);
		
		btnSend = new Button(this);
		btnSend.setText("ì]ëó");
		btnSend.setOnClickListener(this);;
		btnSend.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(btnSend);
		
		editText = new EditText(this);
		editText.setText("");
		editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(editText);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btnStart) {
			try {
				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
				intent.putExtra(RecognizerIntent.EXTRA_PROMPT,  "âπê∫îFéØ");
				intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
				startActivityForResult(intent, REQUEST_CODE);
			} catch(ActivityNotFoundException e) {
				Toast.makeText(RecognizeTo.this, e.getMessage(), Toast.LENGTH_LONG);
			}
			
		} else if(v == btnSend) {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_TEXT, editText.getText().toString());
			startActivity(intent);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			String resStr = "";
			
			ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			
			for(int i=0; i < results.size(); i++) {
				resStr += results.get(i);
			}
			editText.setText(editText.getText() + resStr);
		}
	}

	
}
