package com.example.datepickertest1;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DatePickerTest1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_picker_test1);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		
		Calendar c = Calendar.getInstance();
		final int year = c.get(Calendar.YEAR);
		final int month = c.get(Calendar.MONTH);
		final int day = c.get(Calendar.DAY_OF_MONTH);
		
		final TextView text = new TextView(this);
		text.setText(String.format("%d 年 %d 月 %d 日", year, month+1, day));
		layout.addView(text);
		
		final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() 
			
		{
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				text.setText(String.format("%d 年 %d 月 %d 日", year, monthOfYear+1, dayOfMonth));
			}
		};
		
		final Button button = new Button(this);
		button.setText("ダイアログの表示");
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialog datePickerDialog = new DatePickerDialog(DatePickerTest1.this, dateSetListener, year, month, day);
				datePickerDialog.show();
					
				
			}
		});
		
		layout.addView(button);
	}


}
