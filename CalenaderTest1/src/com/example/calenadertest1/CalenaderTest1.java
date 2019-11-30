package com.example.calenadertest1;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class CalenaderTest1 extends Activity {

	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calenader_test1);
		textView = (TextView)findViewById(R.id.textView1);
		
		
		getCalendars(); //�J�����_�[���X�g���擾���A���ꂼ��̃J�����_�[ID��name��TextView�ɕ\��
		
		
		List<CalendarEvent> events;
		events = getCalendarEventsFromCalendarId(9);	//�J�����_�[ID=9�̃J�����_�[����X�̃C�x���g�����擾
		for(CalendarEvent ev : events) {
			textView.append(ev.getTitle());				//title��TextView�ɒǉ�
			textView.append("\n");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calenader_test1, menu);
		return true;
	}
	
	public void getCalendars() {
			ContentResolver resolver = this.getContentResolver();
			Uri calendarUri = Uri.parse("content://com.android.calendar/calendars");	//�J�����_�[���X�g���擾����ۂ�URI
			
			String[] projection = new String[] {"_id", "name"};				//���������̃R���������X�g
			String selection = "access_level = ? and name is not null";		//�t�B���^�[
			String[] selectionArgs = new String[] {"700"};					//�t�B���^�[��?�̕����ɓ���������X�g
			Cursor cursor = resolver.query(calendarUri, projection, selection, selectionArgs, null);
			
			if (cursor == null) {													//��L�𖞂������ʂ������Ȃ����
				Toast.makeText(this, "cursor is null", Toast.LENGTH_LONG).show();
				return;																//return
			}
			
			while( cursor.moveToNext() ) {											//�X�̃J�����_�[���擾
				int calendarid = cursor.getInt(cursor.getColumnIndex("_id"));		//�J�����_�[ID���擾 (1, 2, 3...�Ȃ�)
				String name = cursor.getString(cursor.getColumnIndex("name"));		//�J�����_�[�����擾(*****@gmail.com�Ȃ�)
				if (name != null) {
					textView.append(String.valueOf(calendarid));					//TextView�ɃJ�����_�[DI�ƃJ�����_�[����\��
					textView.append(" / ");
					textView.append(name);
					textView.append("\n");
				}
			}
			textView.append("\n");
			
			cursor.close();
			return;
	}
	
	public List<CalendarEvent> getCalendarEventsFromCalendarId(int calendarId) {
		List<CalendarEvent> events = new ArrayList<CalendarEvent>();
		
		ContentResolver resolver = this.getContentResolver();
		Uri calendarUri = Uri.parse("content://com.android.calendar/events");	//�J�����_�[ID����X�̃C�x���g�����擾����ۂ�URI
		
		String[] projection = new String[] {"_id", "title", "description", "eventLocation", "dtstart", "dtend"};	//�J�����_�[���X�g���擾����ۂ�URI
		String selection = "deleted = 0 and calendar_id = ?";
		String[] selectionArgs = new String[] {String.valueOf(calendarId)};
		Cursor cursor = resolver.query(calendarUri, projection, selection, selectionArgs, null);
		
		if (cursor == null) {
			return null;
		}
		
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
			String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
			String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
			String eventLocation = cursor.getString(cursor.getColumnIndexOrThrow("eventLocation"));
			long dtstart = cursor.getLong(cursor.getColumnIndexOrThrow("dtstart"));
			long dtend = cursor.getLong(cursor.getColumnIndexOrThrow("dtend"));
			
			events.add(new CalendarEvent(id, title, description, eventLocation, dtstart, dtend, calendarId));
		}
		
		cursor.close();
		
		return events;
	}
	
	public class CalendarEvent {
		
		private int mEventId;
		private String mTitle;
		private String mDescription;
		private String mEventLocation;
		private long mDtstart;
		private long mDtend;
		private int mCalendarId;
		
		public CalendarEvent(int id, String title, String description, String eventLocation, long dtstart, long dtend, int calendarId){
			this.mEventId 		= id;
			this.mTitle 		= title;
			this.mDescription 	= description;
			this.mEventLocation = eventLocation;
			this.mDtstart 		= dtstart;
			this.mDtend 		= dtend;
			this.mCalendarId	= calendarId;
		}
		
		public int getId() {
			return mEventId;
		}
		
		public String getTitle() {
			return mTitle;
		}
		
		public String getDescription() {
			return mDescription;
		}
		
		public String getEventLocation() {
			return mEventLocation;
		}
		
		public long getDtstart() {
			return mDtstart;
		}
		
		public long getDtend() {
			return mDtend;
		}
		
		public int getCalendarId() {
			return mCalendarId;
		}
	}

}
