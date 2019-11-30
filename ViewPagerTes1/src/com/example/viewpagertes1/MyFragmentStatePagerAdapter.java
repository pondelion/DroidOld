package com.example.viewpagertes1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

	public MyFragmentStatePagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "Page " + position;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		switch(arg0) {
		case 0:
			return new Fragment0();
		case 1:
			return new Fragment1();
		default:
			return new Fragment2();
		}
	}

}
