package com.example.progressdialogtest2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;

public class ProgressDialogTest2 extends Activity implements Runnable{

	private ProgressDialog _progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_progressDialog = new ProgressDialog(this);
		_progressDialog.setTitle("ƒŒƒ“ƒ_ƒŠƒ“ƒO’†");
		_progressDialog.setMessage("‚µ‚Î‚ç‚­‚¨‘Ò‚¿‚­‚¾‚³‚¢");
		_progressDialog.setIndeterminate(false);
		_progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		_progressDialog.setMax(100);
		_progressDialog.show();
		
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run()
	{
		for(int i=0; i<100; i++)
		{
			handler.sendMessage(Message.obtain(handler, i));
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
				
			}
		}
		handler.sendMessage(Message.obtain(handler, -1));
	}
	
	private Handler handler = new Handler()
	{
		
			public void handleMessage(Message msg)
			{
				if(msg.what == -1)
				{
					_progressDialog.dismiss();
					return;
				}
				_progressDialog.setProgress(msg.what);
			}
		
	};

}
