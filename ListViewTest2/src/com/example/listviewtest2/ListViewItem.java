package com.example.listviewtest2;

import android.graphics.Bitmap;

public class ListViewItem {

	public Bitmap thumbnail;
	public String text;
	
	public ListViewItem(Bitmap bmp, String txt) {
		this.thumbnail = bmp;
		this.text = txt;
	}

}
