package net.ollyolly.lifemanager1;

import org.achartengine.GraphicalView;


import net.ollyolly.lifemanager1.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatisticalInformationViewPagerFragment extends Fragment {

	public String mItemName;
	private LinearLayout mLinearLayoutChart;
	private View mLayout;
	private TextView mTextViewAverageValue;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mLayout = inflater.inflate(R.layout.statistical_information_viewpager_fragment, null);
	
		mLinearLayoutChart = (LinearLayout)mLayout.findViewById(R.id.linearlayout_statistical_information_viewpager_chart);
		mTextViewAverageValue = (TextView)mLayout.findViewById(R.id.textView_statistical_information_viewpager_averageValue);
		
		GraphicalView gv;
		gv = (new StatisticalInformationChart()).execute(getActivity());
		mLinearLayoutChart.addView(gv);
		return mLayout;
	}
	
	public void setChartView(final GraphicalView gv) {
		(new Handler()).post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					mLinearLayoutChart.removeAllViews();
					mLinearLayoutChart.addView(gv);
				} catch (Exception e) {
					MainActivity.messageBox(e.getMessage());
				}
			}
			
		});
		
	}
	
	public void setAverageValue(final double averageValue) {
		(new Handler()).post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					mTextViewAverageValue.setText(String.valueOf(averageValue));
				} catch (Exception e) {
					MainActivity.messageBox(e.getMessage());
				}
			}
			
		});
	}
}
