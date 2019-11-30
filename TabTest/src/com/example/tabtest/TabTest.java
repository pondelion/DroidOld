package com.example.tabtest;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_test);
		
		TabHost host = (TabHost) findViewById(R.id.host);
		host.setup();
		TabSpec tab1 = host.newTabSpec("tab1");
		tab1.setIndicator("tab1", 
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		tab1.setContent(R.id.tab1);
		host.addTab(tab1);

		TabSpec tab2 = host.newTabSpec("tab2");
		tab2.setIndicator("tab2", 
				new BitmapDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
		tab2.setContent(R.id.tab2);
		host.addTab(tab2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_test, menu);
		return true;
	}

}
