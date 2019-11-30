package com.example.charttest1;


import org.achartengine.GraphicalView;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.os.Build;



public class ChartTest1 extends ActionBarActivity {

	private static LinearLayout linearLayout2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chart_test1);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		linearLayout2 = (LinearLayout)((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.fragment_chart_test1, null).findViewById(R.id.linearLayout2);
		if (linearLayout2 == null) {
			return;
		}
		//Intent intent = null;
		//intent = (new AverageTemperatureChart()).execute(this);
		//startActivity(intent);
		
		GraphicalView gv;
		gv = (new AverageTemperatureChart()).execute(this);
		linearLayout2.addView(gv);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chart_test1, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_chart_test1,
					container, false);
			/*
			LinearLayout ll = (LinearLayout)rootView.findViewById(R.id.linearLayout2);
			GraphicalView gv;
			gv = (new AverageTemperatureChart()).execute(container.getContext());
			ll.addView(gv);
			*/
			return rootView;
		}
	}

}
