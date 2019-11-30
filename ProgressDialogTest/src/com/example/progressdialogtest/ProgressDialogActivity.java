package com.example.progressdialogtest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.app.ProgressDialog;

public class ProgressDialogActivity extends Activity implements Runnable {

	private ProgressDialog _progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_progressDialog = new ProgressDialog(this);
		_progressDialog.setTitle("ƒŒƒ“ƒ_ƒŠƒ“ƒO’†");
		_progressDialog.setMessage("‚µ‚Î‚ç‚­‚¨‘Ò‚¿‚­‚¾‚³‚¢");
		_progressDialog.setIndeterminate(true);
		_progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		_progressDialog.show();
		Thread thread = new Thread(this);
		thread.start();
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			Thread.sleep(5000);
			
		}
		catch(InterruptedException e)
		{
			
		}
		
		handler.sendEmptyMessage(0);
	}
	
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			_progressDialog.dismiss();
		}
	};

	

}
