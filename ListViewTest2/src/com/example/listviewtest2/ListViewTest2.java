package com.example.listviewtest2;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewTest2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_test2);
		
		ListView listView = (ListView)findViewById(R.id.listView1);
		List<ListViewItem> items = new ArrayList<ListViewItem>();
		for (int i = 0; i < 10; i++) {
			items.add(new ListViewItem(BitmapFactory.decodeResource(getResources(), R.drawable.face), i+"番目"));
		}
		
		listView.setAdapter(new ListViewAdapter(this, items));
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(), position+"番目のアイテムがクリックされました", Toast.LENGTH_LONG).show();
			}
		});
	}

}
