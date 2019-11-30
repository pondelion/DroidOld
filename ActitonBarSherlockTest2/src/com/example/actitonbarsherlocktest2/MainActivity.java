package com.example.actitonbarsherlocktest2;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends SherlockActivity implements ActionBar.TabListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        /* Themeの設定が必ず必要 */
        setTheme(R.style.Theme_Sherlock);
        setContentView(R.layout.activity_main);
 
        /* Tabの利用 */
        final ActionBar mActionBar = getSupportActionBar();
 
        mActionBar.addTab(mActionBar.newTab().setText("tab1").setTabListener(this));
        mActionBar.addTab(mActionBar.newTab().setText("tab2").setTabListener(this));
        mActionBar.addTab(mActionBar.newTab().setText("tab3").setTabListener(this));
 
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
 
    }

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	/*
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
	    menu.add("add")
	        .setIcon(android.R.drawable.ic_menu_add)
	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	    menu.add("Search")
	        .setIcon(android.R.drawable.ic_menu_search)
	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	    menu.add("Save")
	        .setIcon(android.R.drawable.ic_menu_save)
	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	    menu.add("Delete")
	    .setIcon(android.R.drawable.ic_menu_delete)
	    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	 
	    return true;
	}

	*/
}
