package com.example.bluetoothcar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.LinearLayout;

public class BluetoothManager {
	
	//private static final UUID BT_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
	private static final UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private BluetoothAdapter btAdapter;
	private ConnectionThread connectionThread;
	public static final int REQUEST_ENABLE_BT = 1;
	private byte[] receivedData = new byte[1024];
	
	
	public BluetoothManager() {
		btAdapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	public void enableBluetooth(Activity activity) {
		if (!btAdapter.isEnabled()) {
			Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			activity.startActivityForResult(intent, REQUEST_ENABLE_BT);
		}
	}

	public Map<String, String> getPairedBluetoothDeviceList() {
		HashMap<String, String> bluetoothDeviceList = new HashMap<String, String>();
		
		if (btAdapter.isEnabled()) {
		

			Set<BluetoothDevice> pairedDeviceSet = btAdapter.getBondedDevices();
			if (pairedDeviceSet.size() > 0) {
				for (BluetoothDevice device : pairedDeviceSet) {
					bluetoothDeviceList.put(device.getName(), device.getAddress());
				}
			}
		
		}
		return bluetoothDeviceList;
		
		
	}
	
	public void startSearchingBluetoothDevice(Activity activity, BluetoothBroadcastReceiver receiver) {
		
		IntentFilter filter;
		filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		activity.registerReceiver(receiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		activity.registerReceiver(receiver, filter);
		
		if (btAdapter.isDiscovering()) {
			btAdapter.cancelDiscovery();
		}
		
		btAdapter.startDiscovery();
	}
	public boolean isSearching() {
		return btAdapter.isDiscovering();
	}
	
	public void cancelSeaching() {
		if (btAdapter.isDiscovering()) {
			btAdapter.cancelDiscovery();
		}
	}
	
	public synchronized void connect(BluetoothDevice device) {
		this.connectionThread = new ConnectionThread(device);
		this.connectionThread.start();
	}
	
	public BluetoothDevice getBluetoothDevice(String addr) {
		
		return btAdapter.getRemoteDevice(addr);
	}
	
	private class ConnectionThread extends Thread {
		private BluetoothDevice btDevice;
		private BluetoothSocket btSocket;
		private InputStream input;
		private OutputStream output;
		
		public ConnectionThread(BluetoothDevice device) {
			this.btDevice = device;
			try {
				this.btSocket = this.btDevice.createRfcommSocketToServiceRecord(BT_UUID);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		public void run() {
			btAdapter.cancelDiscovery();
			
			try {
				
				btSocket.connect();
				MainActivity.showMessage("btSocket()後");
			} catch (Exception e) {
				e.printStackTrace();
				MainActivity.showMessage("接続できませんでした");
				try {
					btSocket.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					return;
				}
				return;
			}
			
			try {
				this.input = btSocket.getInputStream();
				this.output = btSocket.getOutputStream();
			} catch (IOException e) {
				MainActivity.showMessage("接続できませんでした");
				e.printStackTrace();
				try {
					btSocket.close();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					return;
				}
				return;
			}
			
			int bytes;
			while (true ) {
				try {
					bytes = input.read(receivedData);
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
		}
		
		public void write(byte[] buf) {
			try {
				output.write(buf);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void close() {
			try {
				btSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public static abstract class BluetoothBroadcastReceiver extends BroadcastReceiver {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			String action = intent.getAction();
			
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				onActionFound(context, intent);
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				onActionDiscoveryFinished(context, intent);
			}
		}
		
		abstract void onActionFound(Context context, Intent intent);
		abstract void onActionDiscoveryFinished(Context context, Intent intent);
	}
}
