package com.example.alertdialogtest;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;

public class AlertDialogTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final CharSequence[] val = {"“Œ‹“s", "‘åã•{", "–kŠC“¹"};
		builder.setTitle("ƒ^ƒCƒgƒ‹");
		builder.setItems(val, new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				Log.d("Sample", val[which]+"‚ª‘I‘ğ‚³‚ê‚Ü‚µ‚½");
			}
		});
		builder.create().show();
	}

}
