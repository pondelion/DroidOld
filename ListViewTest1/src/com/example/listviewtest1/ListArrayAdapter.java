package com.example.listviewtest1;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListArrayAdapter extends ArrayAdapter<ListItem> {

	private List<ListItem> items;
	int mLastAnimationPosition = 0;
	
	public ListArrayAdapter(Context context, List<ListItem> items) {
		super(context, -1, items);
		// TODO Auto-generated constructor stub
		this.items = items;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Context context = getContext();
		
		ListItem item = items.get(position);
		
		if(convertView == null) {
			LinearLayout layout = new LinearLayout(context);
			layout.setPadding(10, 10, 10, 10);
			layout.setBackgroundColor(Color.WHITE);
			convertView = layout;
			
			ImageView imageView = new ImageView(context);
			imageView.setTag("icon");
			layout.addView(imageView);
			
			TextView textView = new TextView(context);
			textView.setTag("text");
			textView.setText(item.text);
			textView.setTextColor(Color.rgb(0, 0, 0));
			textView.setPadding(10, 20, 10, 20);
			layout.addView(textView);
		}
		
		ImageView imageView = (ImageView)convertView.findViewWithTag("icon");
		imageView.setImageBitmap(item.icon);
		TextView textView = (TextView)convertView.findViewWithTag("text");
		textView.setText(item.text);
		
		if (mLastAnimationPosition < position) {
			Animation animation = AnimationUtils.loadAnimation(context, R.anim.motion);
			convertView.startAnimation(animation);
			mLastAnimationPosition = position;
		}
		
		return convertView;
	}

}
