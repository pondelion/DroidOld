package net.ollyolly.lifemanager1;

import java.util.ArrayList;
import java.util.List;

import net.ollyolly.lifemanager1.R;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


public class DataInputActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private Button mButtonSave;
	private DatePicker mDatePicker;
	private List<DataInputViewPagerFragment> mDataInputViewPagerFragmentList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mDataInputViewPagerFragmentList = new ArrayList<DataInputViewPagerFragment>();
		
		List<String> usedItemNameList = MainActivity.mItemNamesDao.getUsedItemNameList();;
		for (int i = 0; i < MainActivity.mItemNamesDao.getUsedItemCount(); i++) {
			DataInputViewPagerFragment fragment = new DataInputViewPagerFragment();
			mDataInputViewPagerFragmentList.add(fragment);
			fragment.mItemName = usedItemNameList.get(i);
			//fragment.mTimePickerStartTime.setEnabled(false);
			//fragment.mTimePcikerEndTime.setEnabled(false);
		}
		setContentView(R.layout.data_input);
	
		mViewPager = (ViewPager)findViewById(R.id.pager);
		
		mViewPager.setAdapter(new DataInputFragmentStatePagerAdapter(getSupportFragmentManager(), mDataInputViewPagerFragmentList));
		
		mDatePicker = (DatePicker)findViewById(R.id.datePicker_datainput);
		
		mButtonSave = (Button)findViewById(R.id.button_datainput_save);
		mButtonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				List<Data> dataList;
				dataList = getAllInputData();
				if (dataList.size() > 0) {
					
					MainActivity.mDataDao.saveData(mDatePicker.getYear(), mDatePicker.getMonth()+1, mDatePicker.getDayOfMonth(), dataList);
				}
				
			}
			
		});
		
		
		//update();
	}
	
	private List<Data> getAllInputData() {	//チェックが少なくとも１つついている項目の開始時間と終了時間のデータのリストを返す。チェックがついていない時間に関しては-1をセットしている
		List<Data> dataList = new ArrayList<Data>();
		boolean saveFlag;
		for (DataInputViewPagerFragment fragment : mDataInputViewPagerFragmentList) {
			Data data = new Data();
			saveFlag = false;
			data.itemName = fragment.mItemName;
			if (fragment.mCheckBoxStartTime.isChecked()) {
				data.startTime = fragment.mTimePickerStartTime.getCurrentHour()*60 + fragment.mTimePickerStartTime.getCurrentMinute();
				saveFlag = true;
			} else {
				data.startTime = -1;
			}
			if (fragment.mCheckBoxEndTime.isChecked()) {
				data.endTime = fragment.mTimePickerEndTime.getCurrentHour()*60 + fragment.mTimePickerEndTime.getCurrentMinute();
				saveFlag = true;
			} else {
				data.endTime = -1;
			}
			
			if (saveFlag) {
				dataList.add(data);
			}
		}
		
		return dataList;
	}
	
	private void update() {
		List<Data> dataList;
		
		dataList = MainActivity.mDataDao.getAllData(mDatePicker.getYear(), mDatePicker.getMonth()+1, mDatePicker.getDayOfMonth());
		
		if (dataList != null) {
			
			for (final Data data : dataList) {
				for (final DataInputViewPagerFragment fragment : mDataInputViewPagerFragmentList) {
					
					if (data.itemName.equals(fragment.mItemName)) {
						
						
						if (data.startTime >= 0) {
							Handler handler = new Handler();
							handler.post(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									try {
										fragment.mTimePickerStartTime.setCurrentHour(5);//data.startTime/60);
										fragment.mTimePickerStartTime.setCurrentMinute(30);//data.startTime%60);
									} catch (Exception e) {
										MainActivity.messageBox("failed to set start time data");
										
									}
								}
								
							});
								
						}
						
						if (data.endTime >= 0) {
							Handler handler = new Handler();
							handler.post(new Runnable() {

								@Override
								public void run() {
									try {
										fragment.mTimePickerEndTime.setCurrentHour(data.endTime/60);
										fragment.mTimePickerEndTime.setCurrentMinute(data.endTime%60);
									} catch (Exception e) {
										MainActivity.messageBox("failed to set end time data");
									}
								}
								
							});
						}
						
						break;
					}
					
				}
			}
		}
		
	}
	
	private void setTimePickerTimeByItemName(String itemName, int hour, int minute) {
		
	}
	
	
}
