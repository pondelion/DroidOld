package com.ollyolly.calllogtest;

import java.util.Date;

import android.os.Bundle;
import android.provider.CallLog;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.view.Menu;
import android.widget.TextView;

public class CallLogTest extends Activity {

	private TextView txtViewCallLogData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_log_test);
		
		txtViewCallLogData = (TextView)findViewById(R.id.textView2);
		getCallLogData();
	}

	private void getCallLogData() {
		
		String[] proj = new String[] {
				CallLog.Calls.TYPE,
				CallLog.Calls.CACHED_NAME,
				CallLog.Calls.DATE,
				CallLog.Calls.NUMBER,
				CallLog.Calls.DURATION,
		};
		
		String order = CallLog.Calls.CACHED_NAME + " asc";
		Cursor cursor = ((ContentResolver)getContentResolver()).query(CallLog.Calls.CONTENT_URI,
				proj,
				null,
				null,
				order);
		
		if (cursor == null) {
			return;
		}
		
		txtViewCallLogData.setText("");
		
		cursor.moveToFirst();
		String val;
		for (int i = 0; i < cursor.getCount(); i++) {
			
			val = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
			txtViewCallLogData.append(val != null ? val : "null");
			txtViewCallLogData.append("/");
			
			val = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
			txtViewCallLogData.append(val != null ? val : "null");
			txtViewCallLogData.append("/");
			
			txtViewCallLogData.append((new Date(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)))).toLocaleString());
			txtViewCallLogData.append("/");
			
			val = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
			txtViewCallLogData.append(val != null ? val : "null");
			txtViewCallLogData.append("/");
			
			txtViewCallLogData.append(String.valueOf(cursor.getInt(cursor.getColumnIndex(CallLog.Calls.DURATION))));
			txtViewCallLogData.append("\n");
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return;
	}

	
}
