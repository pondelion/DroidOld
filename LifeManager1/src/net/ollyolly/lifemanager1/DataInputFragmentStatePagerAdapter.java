package net.ollyolly.lifemanager1;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class DataInputFragmentStatePagerAdapter extends
		FragmentStatePagerAdapter {

	private List<DataInputViewPagerFragment> mDataInputViewPagerFragment;
	
	public DataInputFragmentStatePagerAdapter(FragmentManager fm, List<DataInputViewPagerFragment> fragmentList) {
		super(fm);
		// TODO Auto-generated constructor stub
		mDataInputViewPagerFragment = fragmentList;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		
		//return new DataInputViewPagerFragment();
		return mDataInputViewPagerFragment.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return MainActivity.mItemNamesDao.getUsedItemCount();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return MainActivity.mItemNamesDao.getUsedItemNameList().get(position).toString();
	}
}
