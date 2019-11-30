package com.example.servercheck;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ServerCheck extends Activity implements OnClickListener {

	private static final int MENU_ID_ITEM1 = Menu.FIRST;
	private static final int MENU_ID_ITEM2 = Menu.FIRST + 1;
	
	private static final String DEF_HTTP = "http://";
	
	private EditText edit01;
	private EditText edit02;
	private EditText edit03;
	private TextView txtView;
	private Button btnConnect;
	
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
		
		edit02 = new EditText(this);
		edit02.setText("");
		edit02.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(edit02);
		
		edit03 = new EditText(this);
		edit03.setText("");
		edit03.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(edit03);
		
		btnConnect = new Button(this);
		btnConnect.setText("ê⁄ë±");
		btnConnect.setOnClickListener(this);
		btnConnect.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(btnConnect);
		
		txtView = new TextView(this);
		txtView.setText("ê⁄ë±åãâ ÇÇ±Ç±Ç…ï\é¶ÇµÇ‹Ç∑");
		txtView.setLayoutParams(new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(txtView);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		MenuItem item1 = menu.add(Menu.NONE, MENU_ID_ITEM1, Menu.NONE, "ê›íËçÌèú");
		item1.setIcon(android.R.drawable.ic_menu_delete);
		MenuItem item2 = menu.add(Menu.NONE, MENU_ID_ITEM2, Menu.NONE, "èIóπ");
		item2.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case MENU_ID_ITEM1:
			SharedPreferences prefer = getPreferences(MODE_WORLD_READABLE);
			SharedPreferences.Editor editor = prefer.edit();
			editor.clear();
			editor.commit();
			getPrefer();
			return true;
		case MENU_ID_ITEM2:
			finish();
			return true;
			
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String retStr = "";
		if(!(DEF_HTTP.equals(edit01.getText().toString()))) {
			retStr = doGet(edit01.getText().toString());
			txtView.setText(edit01.getText().toString() + " " + retStr);
		}
		
		if(!(DEF_HTTP.equals(edit02.getText().toString()))) {
			retStr = doGet(edit02.getText().toString());
			txtView.setText(txtView.getText().toString() + "\n" + edit02.getText().toString() + " " + retStr);
		}
		
		if(!(DEF_HTTP.equals(edit03.getText().toString()))) {
			retStr = doGet(edit03.getText().toString());
			txtView.setText(txtView.getText().toString() + "\n" + edit03.getText().toString() + " " + retStr);
		}
	}
	
	public String doGet(String url) {
		try{
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet method = new HttpGet(url);
			
			HttpResponse response = client.execute(method);
			int status = response.getStatusLine().getStatusCode();
			return "Status: " + status;
		} catch (Exception e) {
			return "Error:" + e.getMessage();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getPrefer();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		SharedPreferences prefer = getPreferences(MODE_WORLD_READABLE);
		SharedPreferences.Editor editor = prefer.edit();
		editor.putString("server1", edit01.getText().toString());
		editor.putString("server2", edit02.getText().toString());
		editor.putString("server3", edit03.getText().toString());
	}
	
	private void getPrefer() {
		SharedPreferences prefer = getPreferences(MODE_WORLD_READABLE);
		edit01.setText(prefer.getString("server01", DEF_HTTP));
		edit02.setText(prefer.getString("server02", DEF_HTTP));
		edit03.setText(prefer.getString("server03", DEF_HTTP));
	}

}
