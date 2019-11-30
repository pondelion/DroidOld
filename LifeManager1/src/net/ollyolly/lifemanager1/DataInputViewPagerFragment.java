package net.ollyolly.lifemanager1;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import net.ollyolly.lifemanager1.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TimePicker;

public class DataInputViewPagerFragment extends Fragment {

	public CheckBox mCheckBoxStartTime;
	public CheckBox mCheckBoxEndTime;
	public TimePicker mTimePickerStartTime;
	public TimePicker mTimePickerEndTime;
	private Button mButtonStartTimeNow;
	private Button mButtonEndTimeNow;
	public String mItemName;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.data_input_viewpager_fragment, null);
		mCheckBoxStartTime = (CheckBox)v.findViewById(R.id.checkBox_starttime);
		mCheckBoxEndTime = (CheckBox)v.findViewById(R.id.checkBox_endtime);
		mTimePickerStartTime = (TimePicker)v.findViewById(R.id.timePicker_starttime);
		mTimePickerEndTime = (TimePicker)v.findViewById(R.id.timePicker_endtime);
		mTimePickerStartTime.setEnabled(false);
		mTimePickerEndTime.setEnabled(false);
		mCheckBoxStartTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				mTimePickerStartTime.setEnabled(isChecked);
			}
			
		});
		mCheckBoxEndTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				mTimePickerEndTime.setEnabled(isChecked);
			}
			
		});
		mButtonStartTimeNow = (Button)v.findViewById(R.id.button_starttime_now);
		mButtonEndTimeNow	= (Button)v.findViewById(R.id.button_endtime_now);
		mButtonStartTimeNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar cal;
				Locale loc = new Locale("ja", "JP", "JP");
				cal = Calendar.getInstance(loc);
				mTimePickerStartTime.setCurrentHour(cal.get(Calendar.HOUR));
				mTimePickerStartTime.setCurrentMinute(cal.get(Calendar.MINUTE));
			}
			
		});
		mButtonEndTimeNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar cal;
				Locale loc = new Locale("ja", "JP", "JP");
				cal = Calendar.getInstance(loc);
				mTimePickerEndTime.setCurrentHour(cal.get(Calendar.HOUR));
				mTimePickerEndTime.setCurrentMinute(cal.get(Calendar.MINUTE));
			}
			
		});
		
		return v;
	}
}
