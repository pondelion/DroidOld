package com.example.tablelayouttest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TableLayoutTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_layout_test);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.table_layout_test, menu);
		return true;
	}

}
