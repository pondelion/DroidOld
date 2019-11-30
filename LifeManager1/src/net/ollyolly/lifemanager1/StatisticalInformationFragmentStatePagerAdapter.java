package net.ollyolly.lifemanager1;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class StatisticalInformationFragmentStatePagerAdapter extends
		FragmentStatePagerAdapter {

	
	public List<StatisticalInformationViewPagerFragment> mStatisticalInformationViewPagerFragmentList;
	public StatisticalInformationActivity mStatisticalInformationActivity;
	
	public StatisticalInformationFragmentStatePagerAdapter(FragmentManager fm, List<StatisticalInformationViewPagerFragment> fragmentList, StatisticalInformationActivity sia) {
		super(fm);
		// TODO Auto-generated constructor stub
		mStatisticalInformationViewPagerFragmentList = fragmentList;
		mStatisticalInformationActivity = sia;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		mStatisticalInformationActivity.updateChartView(mStatisticalInformationViewPagerFragmentList.get(arg0).mItemName);
		return mStatisticalInformationViewPagerFragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mStatisticalInformationViewPagerFragmentList.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return MainActivity.mItemNamesDao.getUsedItemNameList().get(position).toString();
	}

}
