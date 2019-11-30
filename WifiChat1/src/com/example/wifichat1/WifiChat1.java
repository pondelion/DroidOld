package com.example.wifichat1;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WifiChat1 extends Activity implements OnClickListener {

	
	static enum STATE { STATE_NONE, STATE_ACCEPTING, STATE_CONNECTING, STATE_CONNECTED };
	private EditText editSend;
	private EditText editChatlog;
	private Button buttonSend;
	private Button buttonConnect;
	private Button buttonDisconnect;
	private TextView textClient;
	private STATE mCurrentState;
	private Handler handler = new Handler();
	private String IP;
	private Integer port;
	private Socket clientSocket;
	private InputStream input;
	private OutputStream output;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi_chat1);
		
		editSend = (EditText)findViewById(R.id.editText1);
		editChatlog = (EditText)findViewById(R.id.editText2);
		buttonSend = (Button)findViewById(R.id.button1);
		buttonConnect = (Button)findViewById(R.id.button2);
		buttonDisconnect = (Button)findViewById(R.id.button3);
		textClient = (TextView)findViewById(R.id.textView1);
		
		textClient.setTextColor(Color.RED);
		
		buttonSend.setOnClickListener(this);
		buttonConnect.setOnClickListener(this);
		buttonDisconnect.setOnClickListener(this);
		buttonDisconnect.setEnabled(false);
		
		mCurrentState = STATE.STATE_NONE;
		
		port = 4416;
		IP = "192.168.0.4";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wifi_chat1, menu);
		return true;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		appendText("onStart()");
		
		//waitConnection();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		disconnect();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == buttonSend) {
			if (mCurrentState == STATE.STATE_CONNECTED) {
				this.send(editSend.getText().toString());
			} else {
				Toast.makeText(this, "接続していません", Toast.LENGTH_LONG).show();
			}
		} else if (v == buttonConnect ) {
			if (mCurrentState == STATE.STATE_CONNECTED) {
				connect();
			}
			connect();
		} else if (v == buttonDisconnect ) {
			if (mCurrentState == STATE.STATE_CONNECTED) {
				disconnect();
			}
		}
	}
	
	private void appendText(final String text) {
		
		handler.post(new Runnable() {
			public void run() {
				editChatlog.setText(editChatlog.getText().toString() + "\n" + text);
			}
		});
	}
	
	private void waitConnection() {
		WaitConnectionThread wct = new WaitConnectionThread(port);
		wct.start();
	}
	
	private void connect() {
		
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("接続先IPとポート番号を入力してください");
		LayoutInflater inflater = LayoutInflater.from(this);
		final View layout = inflater.inflate(R.layout.connectlayout, null);
		((EditText)layout.findViewById(R.id.editText3)).setText(IP);
		((EditText)layout.findViewById(R.id.editText4)).setText(port.toString());
		dialog.setView(layout);
		dialog.setPositiveButton("CONNECT", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				EditText editTextIP = (EditText)layout.findViewById(R.id.editText3);
				EditText editTextPort = (EditText)layout.findViewById(R.id.editText4);
				
				IP = editTextIP.getText().toString();
				port = Integer.parseInt(editTextPort.getText().toString());
				
				try {
					appendText(IP + " (" + port.toString() + ")" + "へ接続中...");
					clientSocket = new Socket(IP, port);
					input = clientSocket.getInputStream();
					output = clientSocket.getOutputStream();
					appendText(IP + "へ接続しました");
					mCurrentState = STATE.STATE_CONNECTED;
					buttonDisconnect.setEnabled(true);
					buttonConnect.setEnabled(false);
					ReceiveDataThread rdt = new ReceiveDataThread();
					rdt.start();
				} catch (Exception e) {
					appendText(IP + "へ接続失敗しました : " + e.getMessage());
				}
			}
		});
		dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		dialog.show();
	}
	
	private void disconnect() {
		if (mCurrentState != STATE.STATE_CONNECTED) {
			return;
		}
		
		try {
			input.close();
			output.close();
			
			clientSocket.close();
			clientSocket.close();
			clientSocket.close();
			clientSocket = null;
			appendText(IP+"から切断しました");
			mCurrentState = STATE.STATE_NONE;
			buttonConnect.setEnabled(true);
			buttonDisconnect.setEnabled(false);
		} catch (Exception e) {
			appendText("Exception in disconnect() :" + e.getMessage());
		}
		
	}
	
	private void send(String text) {
		if (mCurrentState != STATE.STATE_CONNECTED) {
			return;
		}
		
		try {
			output.write(text.getBytes("UTF8"));
			appendText("あなた : " + text);
			editSend.setText("");
		} catch (Exception e) {
			appendText("send() failed : " + e.getMessage());
		}
	}
	
	class WaitConnectionThread extends Thread {
		
		ServerSocket serverSocket;
		Socket acceptSocket;
		int mWaitPort;
		
		WaitConnectionThread(int port) {
			super();
			mWaitPort = port;
		}
		
		public void run() {
			
			try {
				serverSocket = new ServerSocket(4416);
				appendText("ポート" + mWaitPort + "で接続待ち...");
				while(true) {
					
					mCurrentState = STATE.STATE_ACCEPTING;
					acceptSocket = serverSocket.accept();
					
					if (mCurrentState == STATE.STATE_CONNECTED) {
						OutputStream out = acceptSocket.getOutputStream();
						byte[] sendData = "先着がいます...切断します".getBytes("UTF8");
						out.write(sendData);
						out.flush();
						out.close();
						acceptSocket.close();
						acceptSocket = null;
					} else {
						clientSocket = acceptSocket;
						mCurrentState = STATE.STATE_CONNECTED;
						IP = acceptSocket.getLocalAddress().toString();
						input = clientSocket.getInputStream();
						output = clientSocket.getOutputStream();
						appendText(IP + "と接続しました");
						port = mWaitPort;
						ReceiveDataThread rdt = new ReceiveDataThread();
						rdt.start();
						
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				appendText("Error in WaitConnectionThread : " + e.getMessage());
			}
			
		}
	}
	
	class ReceiveDataThread extends Thread {
		
		
		public void run() {
			
			try {
				
				appendText("ReceiveDataThread start");
				byte[] recvData = new byte[1024];
				int length;
				while (mCurrentState == STATE.STATE_CONNECTED && clientSocket.isConnected()) {
					
					length = input.read(recvData);
					
					if(length <= 0) {
						throw new Exception();
					}
					
					appendText("あいて : " + new String(recvData, 0 ,length, "UTF8"));
					
				}
			} catch (Exception e) {
				appendText("Error in ReceiveDataThread : " + e.getMessage());
			}
		}
	}

}
