package com.example.tabtest1;



import android.os.Bundle;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

public class TabTest1 extends Activity {

	private ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_test1);
		
		TabHost host = (TabHost) findViewById(android.R.id.tabhost);
		host.setup();
		TabSpec tab1 = host.newTabSpec("tab1");
		tab1.setIndicator("tab1", 
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		tab1.setContent(R.id.first_content);
		host.addTab(tab1);

		TabSpec tab2 = host.newTabSpec("tab2");
		tab2.setIndicator("tab2", 
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		tab2.setContent(R.id.second_content);
		host.addTab(tab2);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_test1, menu);
		return true;
	}

}
