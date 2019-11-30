package com.example.listviewtest2;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewAdapter extends ArrayAdapter<ListViewItem> {

	private LayoutInflater layoutInflater;
	
	public ListViewAdapter(Context context, List<ListViewItem> items) {
		super(context, -1, items);
		// TODO Auto-generated constructor stub
		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.listviewitem_layout, null);
		}
		
		ListViewItem listViewItem = (ListViewItem)getItem(position);
		ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView1);
		imageView.setImageBitmap(listViewItem.thumbnail);
		
		TextView textView = (TextView)convertView.findViewById(R.id.textView1);
		textView.setText(listViewItem.text);
		
		return convertView;
		
	}
	

}
