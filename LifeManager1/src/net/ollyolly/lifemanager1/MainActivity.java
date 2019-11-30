package net.ollyolly.lifemanager1;

import net.ollyolly.lifemanager1.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	public static SQLiteDatabase mDb = null;
	public static ItemNamesDao mItemNamesDao = null;
	public static DataDao mDataDao = null;
	private static Button mBtnItemSetting;
	private static Button mBtnDataInput;
	private static Button mBtnStatisticalInformation;
	public static Activity mMainActivity;
	//private static Button mBtn
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar ab;
		ab = getSupportActionBar();
		ab.hide();
		//ab.setDisplayShowHomeEnabled(false);
		//ab.setDisplayShowTitleEnabled(false);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		DatabaseHelper dh = new DatabaseHelper(this);
		mDb = dh.getWritableDatabase();
		
		Log.d("LifeManager1","test1");
		
		mItemNamesDao = new ItemNamesDao(mDb);
		Log.d("LifeManager1","test2");
		mDataDao = new DataDao(mDb);
		Log.d("LifeManager1","test3");
		
		mMainActivity = (MainActivity)this;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			mBtnItemSetting = (Button)rootView.findViewById(R.id.btn_item_setting);
			mBtnItemSetting.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					startActivity(new Intent(getActivity(), ItemSettingActivity.class));
				}
			});
			
			mBtnDataInput = (Button)rootView.findViewById(R.id.btn_data_input);
			mBtnDataInput.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					startActivity(new Intent(getActivity(), DataInputActivity.class));
				}
			});
			
			mBtnStatisticalInformation = (Button)rootView.findViewById(R.id.btn_statistical_information);
			mBtnStatisticalInformation.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(getActivity(), StatisticalInformationActivity.class));
				}
				
			});
			
			return rootView;
		}
	}
	
	public static void messageBox(String message) {
		final String str = message;
		Handler handler = new Handler();
		handler.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.mMainActivity, str, Toast.LENGTH_LONG).show();
			}
			
		});
	}

}
