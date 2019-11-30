package com.example.gsontest1;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	static Data data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		data =  new Data("hello world!", 121);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	private static User fromJSON() {
		String json = "{\"address\":\"Japan\",\"name\":\"test\",\"age\":25}";
		Gson gson = new Gson();
		User user = gson.fromJson(json, User.class);
		return user;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			TextView textView = (TextView)rootView.findViewById(R.id.textView1);
			textView.setText(fromJSON().address);
			
			Gson gson = new Gson();
			String str = gson.toJson(data);
			textView.setText(str);
			return rootView;
		}
	}

	
	public class User {
		public String name;
		public int age;
		public String address;
		public User() {}
	}
	
	public class Data {
		@SerializedName("message")
		public String mMessage;
		
		@SerializedName("count")
		public int mCount;
		
		public Data(String message, int count) {
			mMessage = message;
			mCount = count;
		}
	}
	
}
