package net.ollyolly.lifemanager1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;

import net.ollyolly.lifemanager1.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

public class StatisticalInformationActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private Button mButtonPeriodSetting;
	private TextView mTextViewPeriodStart;
	private TextView mTextViewPeriodEnd;
	private Spinner mSpinnerMode;
	private final String[] MODE_STRING = {"開始時間", "終了時間", "開始時間&終了時間", "終了時間-開始時間"};
	public static enum MODE {START, END, START_AND_END, END_MINUS_START};
	private int mPeriodStartYear, mPeriodStartMonth, mPeriodStartDay;
	private int mPeriodEndYear, mPeriodEndMonth, mPeriodEndDay;
	private List<StatisticalInformationViewPagerFragment> mStatisticalInformationViewPagerFragmentList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.statistical_information);
		
		mStatisticalInformationViewPagerFragmentList = new ArrayList<StatisticalInformationViewPagerFragment>();
		List<String> usedItemNameList = MainActivity.mItemNamesDao.getUsedItemNameList();;
		for (int i = 0; i < MainActivity.mItemNamesDao.getUsedItemCount(); i++) {
			StatisticalInformationViewPagerFragment fragment = new StatisticalInformationViewPagerFragment();
			mStatisticalInformationViewPagerFragmentList.add(fragment);
			fragment.mItemName = usedItemNameList.get(i);
		}
		
		
		mViewPager = (ViewPager)findViewById(R.id.pager_statistical_information);
		mViewPager.setAdapter(new StatisticalInformationFragmentStatePagerAdapter(getSupportFragmentManager(), mStatisticalInformationViewPagerFragmentList, this));
		
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		mButtonPeriodSetting = (Button)findViewById(R.id.button_statistical_information_period_setting);
		mButtonPeriodSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
				final View layout = inflater.inflate(R.layout.dialog_statistical_information_period_setting, null);
				DatePicker start 	= (DatePicker)layout.findViewById(R.id.datePicker_statistical_information_period_setting_start);
				DatePicker end		= (DatePicker)layout.findViewById(R.id.datePicker_statistical_information_period_setting_end);
				start.updateDate(mPeriodStartYear, mPeriodStartMonth-1, mPeriodStartDay);
				end.updateDate(mPeriodEndYear, mPeriodEndMonth-1, mPeriodEndDay);
				
				builder.setTitle("期間設定");
				builder.setView(layout);
				builder.setPositiveButton("セット", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						DatePicker start 	= (DatePicker)layout.findViewById(R.id.datePicker_statistical_information_period_setting_start);
						DatePicker end		= (DatePicker)layout.findViewById(R.id.datePicker_statistical_information_period_setting_end);
						
						setPeriodStartDate(start.getYear(), start.getMonth()+1, start.getDayOfMonth());
						setPeriodEndDate(end.getYear(), end.getMonth()+1, end.getDayOfMonth());
						
						updateChartView("sleep");
					}
					
				});
				builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				builder.show();
			}
			
		});
		
		mTextViewPeriodStart 	= (TextView)findViewById(R.id.textView_statistical_information_period_start);
		mTextViewPeriodEnd		= (TextView)findViewById(R.id.textView_statistical_information_period_end);
		
		Calendar cal = Calendar.getInstance();
		
		setPeriodEndDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		
		cal.add(Calendar.MONTH, -1);
		
		setPeriodStartDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		
		mSpinnerMode = (Spinner)findViewById(R.id.spinner_statistical_information_mode);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		for (String mode : MODE_STRING) {
			adapter.add(mode);
		}
		
		mSpinnerMode.setAdapter(adapter);
		mSpinnerMode.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				updateChartView(mStatisticalInformationViewPagerFragmentList.get(mViewPager.getCurrentItem()).mItemName);
				double averageValue = -1.0;
				averageValue = MainActivity.mDataDao.getAverageValue(mStatisticalInformationViewPagerFragmentList.get(mViewPager.getCurrentItem()).mItemName, getSelectedMode(), mPeriodStartYear, mPeriodStartMonth, mPeriodStartDay, mPeriodEndYear, mPeriodEndMonth, mPeriodEndDay);
				if (averageValue >= 0.0) {
					mStatisticalInformationViewPagerFragmentList.get(mViewPager.getCurrentItem()).setAverageValue(averageValue);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	private void setPeriodStartDate(int year, int month, int day) {
		mPeriodStartYear = year;
		mPeriodStartMonth = month;
		mPeriodStartDay = day;
		mTextViewPeriodStart.setText(String.valueOf(year) + "年" + String.valueOf(month) + "月" + String.valueOf(day) + "日~");
	}
	
	private void setPeriodEndDate(int year, int month , int day) {
		mPeriodEndYear = year;
		mPeriodEndMonth = month;
		mPeriodEndDay = day;
		mTextViewPeriodEnd.setText(String.valueOf(year) + "年" + String.valueOf(month) + "月" + String.valueOf(day) + "日");
	}
	
	public void updateChartView(String itemName) {
		
		//MainActivity.messageBox("updateChartView is called");
		
		MODE selectedMode = getSelectedMode();
		if (selectedMode == null) {
			MainActivity.messageBox("モードが選択されていません");
			return;
		}
		
		StatisticalInformationViewPagerFragment fragment = null;
		
		for (StatisticalInformationViewPagerFragment f : mStatisticalInformationViewPagerFragmentList) {
			if (f.mItemName.equals(itemName)) {
				fragment = f;
				break;
			}
		}
		
		if (fragment == null) {
			MainActivity.messageBox("updateChartView() : fragment == null");
			return;
		}
		
		
		
		List<Data> dataList = MainActivity.mDataDao.getAllDataOfSpecifiedItemAndPeriod(itemName, mPeriodStartYear, mPeriodStartMonth, mPeriodStartDay, mPeriodEndYear, mPeriodEndMonth, mPeriodEndDay);
		
		if (dataList == null) {
			MainActivity.messageBox("指定項目の指定期間内のデータは存在しません");
			return;
		} else {
			//MainActivity.messageBox("dataList.size() = " + dataList.size());
		}
		
		
		
		List<Date[]> xValues = new ArrayList<Date[]>();
		Date[] tempDate1 = new Date[dataList.size()];
		Date[] tempDate2 = new Date[dataList.size()]; 
		List<double[]> yValues = new ArrayList<double[]>();
		double[] tempTime1 = new double[dataList.size()];
		double[] tempTime2 = new double[dataList.size()];
		int i = 0;
		
		Calendar cal = Calendar.getInstance();
		for (Data data : dataList) {
			cal.setTime(new Date(data.date));
			// tempDate[i] = new Date(data.date); //これは無理らしい
			tempDate1[i] = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			switch (selectedMode) {
			case START:
				tempTime1[i] = (double)(data.startTime/60) + (double)(data.startTime%60)/60;
				break;
			case END:
				tempTime1[i] = (double)(data.endTime/60) + (double)(data.endTime%60)/60;
				break;
			case END_MINUS_START:
				long timeDiff = data.endTime - data.startTime;
				if (timeDiff > 0) {
					tempTime1[i] = (double)(timeDiff/60) + (double)(timeDiff%60)/60;
				}
				break;
			case START_AND_END:
				tempDate2[i] = new Date(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
				tempTime1[i] = (double)(data.startTime/60) + (double)(data.startTime%60)/60;
				tempTime2[i] = (double)(data.endTime/60) + (double)(data.endTime%60)/60;
				break;
			}
			
			i++;
		}
		
		xValues.add(tempDate1);
		yValues.add(tempTime1);
		if (selectedMode == MODE.START_AND_END) {
			xValues.add(tempDate2);
			yValues.add(tempTime2);
		}
		
		/*
		Date[] dates = {new Date(mPeriodStartYear, mPeriodStartMonth-1, mPeriodStartDay), new Date(mPeriodEndYear, mPeriodEndMonth-1, mPeriodEndDay)};
		xValues.add(dates);
		double[] times = {2.3, 25.3};
		yValues.add(times);
		*/
		//GraphicalView gv = (new StatisticalInformationChart()).execute(this, new String[] { "Test"}, xValues, yValues, new int[]{Color.BLUE}, new PointStyle[]{PointStyle.CIRCLE}, "統計情報", "日付", "時間", new Date(mPeriodStartYear, mPeriodStartMonth-1, mPeriodStartDay), new Date(mPeriodEndYear, mPeriodEndMonth - 1, mPeriodEndDay), 0, 24);
		GraphicalView gv;
		if (selectedMode == MODE.START_AND_END) {
			gv = (new StatisticalInformationChart()).execute(this, new String[] { "開始時間", "終了時間"}, xValues, yValues, new int[]{Color.BLUE, Color.GREEN}, new PointStyle[]{PointStyle.CIRCLE, PointStyle.CIRCLE}, "統計情報", "日付", "時間", new Date(mPeriodStartYear, mPeriodStartMonth-1, mPeriodStartDay), new Date(mPeriodEndYear, mPeriodEndMonth - 1, mPeriodEndDay), 0, 24);
		} else {
			gv = (new StatisticalInformationChart()).execute(this, new String[] {getSelectedModeText()}, xValues, yValues, new int[]{Color.BLUE}, new PointStyle[]{PointStyle.CIRCLE}, "統計情報", "日付", "時間", new Date(mPeriodStartYear, mPeriodStartMonth-1, mPeriodStartDay), new Date(mPeriodEndYear, mPeriodEndMonth - 1, mPeriodEndDay), 0, 24);
		}
		if(gv != null) {
			fragment.setChartView(gv);
		} else {
			MainActivity.messageBox("updateChartView:gv == null");
		}
	}
	
	private String getSelectedModeText() {
		return mSpinnerMode.getSelectedItem().toString();
	}
	
	private MODE getSelectedMode() {
		
		String strMode = getSelectedModeText();
		if (strMode.equals(MODE_STRING[0])) {
			return MODE.START;
		} else if (strMode.equals(MODE_STRING[1])) {
			return MODE.END;
		} else if (strMode.equals(MODE_STRING[2])) {
			return MODE.START_AND_END;
		} else if (strMode.equals(MODE_STRING[3])) {
			return MODE.END_MINUS_START;
		}
		
		return null;
	}
}
