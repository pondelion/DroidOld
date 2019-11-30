package com.example.testpreference;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TestPreference extends Activity implements View.OnClickListener {

	private EditText edit01;
	private Button btnPut;
	private Button btnGet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		
		edit01 = new EditText(this);
		edit01.setText("");
		edit01.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(edit01);
		
		btnPut = new Button(this);
		btnPut.setText("ï€ë∂");
		btnPut.setOnClickListener(this);
		btnPut.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(btnPut);
		
		btnGet = new Button(this);
		btnGet.setText("ì«Ç›çûÇ›");
		btnGet.setOnClickListener(this);
		btnGet.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(btnGet);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btnPut)
		{
			Toast.makeText(this, "btnPut", Toast.LENGTH_LONG).show();
			SharedPreferences prefer = getSharedPreferences(
					"TestPreference", MODE_PRIVATE);
			
			SharedPreferences.Editor editor = prefer.edit();
			editor.putString("settings1", edit01.getText().toString());
			editor.commit();
		} else if(v == btnGet) {
			Toast.makeText(this, "btnGet", Toast.LENGTH_LONG).show();
			SharedPreferences prefer = getSharedPreferences(
					"TestPreference", MODE_PRIVATE);
			
			edit01.setText(prefer.getString("settings1", ""));
		}
		
	}

	
}
