package com.example.listviewtest1;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.Window;
import android.widget.ListView;

public class ListViewTest1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		List<ListItem> items = new ArrayList<ListItem>();
		for (int i = 0; i < 30; i++) {
			items.add(new ListItem(res2bmp(this,R.drawable.ic_launcher), "€–Ú"+i));
		}
		
		ListView listView = new ListView(this);
		listView.setScrollingCacheEnabled(false);
		listView.setAdapter(new ListArrayAdapter(this, items));
		listView.setFadingEdgeLength(0);
		listView.setDividerHeight(8);
		setContentView(listView);
	}
	
	public static Bitmap res2bmp(Context context, int resID) {
		return BitmapFactory.decodeResource(context.getResources(), resID);
	}

	

}
