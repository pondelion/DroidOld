package com.example.photoalbum;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoAlbum extends Activity implements OnItemClickListener {

	private ArrayList<Bitmap> photoList = new ArrayList<Bitmap>();
	private ArrayList<String> fileList = new ArrayList<String>();
	private ArrayList<Long> dateList = new ArrayList<Long>();
	private Gallery gallery = new Gallery(this);
	private ImageView imgView = new ImageView(this);
	private TextView txtView = new TextView(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setBackgroundColor(Color.rgb(0, 0, 255));
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		setContentView(layout);
		
		Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
		Cursor cur = this.managedQuery(uri, null, null, null, null);
		
		cur.moveToFirst();
		for (int i = 0; i < cur.getCount(); i++) {
			
			String path = cur.getString(cur.getColumnIndexOrThrow("_data"));
			long datetaken = cur.getLong(cur.getColumnIndexOrThrow("datataken"));
			fileList.add(path);
			dateList.add(datetaken);
			photoList.add(file2bmp(path,150,150));
			
			for (String column : cur.getColumnNames()) {
				android.util.Log.v("columnName", column + "=" + cur.getString(cur.getColumnIndexOrThrow(column)));
			}
			cur.moveToNext();
		}
		
		//gallery = new Gallery(this);
		gallery.setSpacing(3);
		gallery.setAdapter(new GalleryAdapter());
		gallery.setOnItemClickListener(this);
		gallery.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(gallery);
		
		//txtView = new TextView(this);
		txtView.setText("“ú•t‚ð•\Ž¦");
		txtView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(txtView);
		
		//imgView = new ImageView(this);
		imgView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		layout.addView(imgView);
		
	}

	@Override
	public void onItemClick(AdapterView<?> adapView, View view, int position, long id) {
		// TODO Auto-generated method stub
		
		if (adapView == gallery) {
			android.util.Log.v("position", position + "=" + fileList.get(position));
			Bitmap bigPicture = file2bmp(fileList.get(position), 400, 400);
			imgView.setImageBitmap(bigPicture);
			txtView.setText(DateFormat.format("yyyy-MM--dd kk.mm.ss", dateList.get(position)).toString());
			
		}
	}
	
	private Bitmap file2bmp(String path, int maxW, int maxH) {
		BitmapFactory.Options options;
		
		try {
			options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			
			BitmapFactory.decodeFile(path,options);
			int scaleW = options.outWidth / maxW + 1;
			int scaleH = options.outHeight / maxH + 1;
			int scale = Math.max(scaleW, scaleH);
			
			options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;
			options.inSampleSize = scale;
			Bitmap bmp = BitmapFactory.decodeFile(path, options);
			return bmp;
					
		} catch (Exception e) {
			return null;
		}
	}
	
	public class GalleryAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return photoList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView imageView;
			
			if (convertView == null) {
				imageView = new ImageView(PhotoAlbum.this);
				imageView.setLayoutParams(new Gallery.LayoutParams(150,150));
				imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				imageView.setBackgroundColor(Color.BLACK);
				BitmapDrawable bd = new BitmapDrawable(photoList.get(position));
				imageView.setImageDrawable(bd);
			} else {
				imageView = (ImageView)convertView;
			}
			
			return imageView;
		}
		
		
	}


}
