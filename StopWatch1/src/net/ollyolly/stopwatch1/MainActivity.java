package net.ollyolly.stopwatch1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.os.Build;

public class MainActivity extends ActionBarActivity implements TabListener {

	private static Button btnStopwatchStartAndStop;
	private static Button btnStopwatchRecord;
	private static Button btnStopwatchClear;
	private static TextView txtStopwatchTime;
	private static ListView listRecord;
	private static NumberPicker numberPickerTimerHour;
	private static NumberPicker numberPickerTimerMinute;
	private static NumberPicker numberPickerTimerSecond;
	private static Button btnTimerReset;
	private static Button btnTimerStartAndStop;
	private static TextView txtTimerTime;
	private static ToggleButton toggleButtonTimerVibrator;
	private static ToggleButton toggleButtonTimerSound;
	private static ArrayAdapter<String> listRecordAdapter = null;
	private static long startTimestamp;
	private static long stopwatchElapsedTime = 0;
	private static Thread stopwatchCountupThread;
	private static Handler handler = new Handler();
	private static Boolean stopwatchThreadFlag = false;
	private static Boolean bStopwatchRunning = false;
	private static long stopwatchPrevTime = 0;
	private static SimpleDateFormat elapsedTimeFormat = new SimpleDateFormat("HH:mm:ss.SS");
	private static long timerTime = 0;
	private static long timerPrevTimestamp;
	private static Boolean bTimerRunning = false;
	private static long timerCurTime = 0;
	private static long timerPrevTime = 0;
	private static Thread timerCountdownThread;
	private static boolean timerThreadFlag = false;
	private static Vibrator vibrator;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new StopwatchFragment()).commit();
		}
		ActionBar ab;
		ab = getSupportActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);//Tabモード
		ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_SHOW_TITLE);//appタイトル表示
		ab.addTab(ab.newTab().setText("Stopwatch").setTabListener(this));
		ab.addTab(ab.newTab().setText("Timer").setTabListener(this));
		
		ab.setDisplayShowHomeEnabled(false);
		ab.setDisplayShowTitleEnabled(false);
		
		vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
		
		setVolumeControlStream(AudioManager.STREAM_ALARM);
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
	public static class StopwatchFragment extends Fragment {

		
		
		public StopwatchFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			View rootView = inflater.inflate(R.layout.fragment_stopwatch, container,
					false);
			
			elapsedTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT")); 
			
			btnStopwatchStartAndStop 	= (Button)rootView.findViewById(R.id.btnStopwatchStartAndStop);
			btnStopwatchRecord 			= (Button)rootView.findViewById(R.id.btnStopwatchRecord);
			btnStopwatchClear			= (Button)rootView.findViewById(R.id.btnStopwatchClear);
			txtStopwatchTime 			= (TextView)rootView.findViewById(R.id.txtTime);
			listRecord 					= (ListView)rootView.findViewById(R.id.listRecord);
			if(listRecordAdapter == null) {
				
				listRecordAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
				listRecordAdapter.add("LAP / SPLIT");
				
				//listRecord.setAdapter(listRecordAdapter); //※これは反映されないらしい

			}
			
			btnStopwatchStartAndStop.setText(bStopwatchRunning ? "Stop" : "Start");
			btnStopwatchRecord.setText(bStopwatchRunning ? "Record" : "Reset");
			btnStopwatchClear.setEnabled(!bStopwatchRunning);
			
			btnStopwatchStartAndStop.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					//startTimestamp = System.currentTimeMillis();
					bStopwatchRunning = !bStopwatchRunning;
					btnStopwatchStartAndStop.setText(bStopwatchRunning ? "Stop" : "Start");
					btnStopwatchRecord.setText(bStopwatchRunning ? "Record" : "Reset");
					btnStopwatchClear.setEnabled(!bStopwatchRunning);
					
					
					if (bStopwatchRunning) {

						stopwatchThreadFlag = true;
						stopwatchPrevTime = System.currentTimeMillis();
						stopwatchCountupThread = new Thread(new Runnable(){
							@Override
							public void run() {
								
								while(stopwatchThreadFlag) {
									
									handler.post(new Runnable(){
										public void run() {
											stopwatchElapsedTime -= stopwatchPrevTime;
											stopwatchPrevTime = System.currentTimeMillis();
											stopwatchElapsedTime += stopwatchPrevTime;
											Date elapsedTime = new Date(stopwatchElapsedTime);
											//txtStopwatchTime.setText(String.format("%.2f", stopwatchPrevTime + (System.currentTimeMillis() - startTimestamp)*0.001));
											txtStopwatchTime.setText(elapsedTimeFormat.format(elapsedTime));
										}
									});
									
									try {
										Thread.sleep(50);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								}
							}
						});
						stopwatchCountupThread.start();
					} else {
						stopwatchThreadFlag = false;
						stopwatchCountupThread = null;
						//stopwatchPrevTime = Float.parseFloat(txtStopwatchTime.getText().toString());
					}
				}
			});
			
			btnStopwatchRecord.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View view) {
					
					if (bStopwatchRunning) {
						if (listRecordAdapter.getCount() == 1) {
							listRecordAdapter.add(txtStopwatchTime.getText() + "/" + txtStopwatchTime.getText());
						} else {
							
							Date prevRecordTime;
							Date currRecordTime;
							try {
								prevRecordTime = elapsedTimeFormat.parse(listRecordAdapter.getItem(listRecordAdapter.getCount()-1).split("/")[1]);
								currRecordTime = elapsedTimeFormat.parse(txtStopwatchTime.getText().toString());
								listRecordAdapter.add(elapsedTimeFormat.format(currRecordTime.getTime()-prevRecordTime.getTime()) + "/" + txtStopwatchTime.getText());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else {
						txtStopwatchTime.setText("0.00");
						stopwatchElapsedTime = 0;
					}
					
				}
			});
			
			btnStopwatchClear.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					listRecordAdapter.clear();
					listRecordAdapter.add("LAP / SPLIT");
					//stopwatchPrevTime = 0.0f;
				}
			});
			
			return rootView;
		}
		
		@Override
		public void onStart() {
			super.onStart();
			
			if(listRecord != null) {
				listRecord.setAdapter(listRecordAdapter);
			}
			
		}
	}
	
	public static class TimerFragment extends Fragment {
		public TimerFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_timer, container,
					false);
			numberPickerTimerHour	= (NumberPicker)rootView.findViewById(R.id.numberPickerHour);
			numberPickerTimerMinute	= (NumberPicker)rootView.findViewById(R.id.numberPickerMinute);
			numberPickerTimerSecond	= (NumberPicker)rootView.findViewById(R.id.numberPickerSecond);
			btnTimerReset			= (Button)rootView.findViewById(R.id.btnTimerReset);
			btnTimerStartAndStop	= (Button)rootView.findViewById(R.id.btnTimerStartAndStop);
			txtTimerTime			= (TextView)rootView.findViewById(R.id.txtTimerTime);
			toggleButtonTimerVibrator	= (ToggleButton)rootView.findViewById(R.id.toggleVivrator);
			toggleButtonTimerSound		= (ToggleButton)rootView.findViewById(R.id.toggleSound);
			
			numberPickerTimerHour.setMaxValue(99);
			numberPickerTimerHour.setMinValue(0);
			numberPickerTimerMinute.setMaxValue(59);
			numberPickerTimerMinute.setMinValue(0);
			numberPickerTimerSecond.setMaxValue(59);
			numberPickerTimerSecond.setMinValue(0);
			
			btnTimerReset.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					timerTime = (numberPickerTimerHour.getValue()*3600 + numberPickerTimerMinute.getValue()*60 + numberPickerTimerSecond.getValue())*1000;
					txtTimerTime.setText(elapsedTimeFormat.format(timerTime));
				}
			});
			
			btnTimerStartAndStop.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					bTimerRunning = !bTimerRunning;
					btnTimerStartAndStop.setText(bTimerRunning ? "Stop" : "Start");
					
					if (bTimerRunning) {
						btnTimerReset.setEnabled(false);
						try {
							timerTime = elapsedTimeFormat.parse(txtTimerTime.getText().toString()).getTime();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//txtTimerTime.setText(elapsedTimeFormat.format(timerTime));
						timerThreadFlag = true;
						timerPrevTimestamp = System.currentTimeMillis();
						Log.d("stopwacth", "1");
						timerCountdownThread = new Thread(new Runnable() {
							long curTimestamp;
							@Override
							public void run() {
								while(timerThreadFlag) {
									//Log.d("stopwacth", "2");
									curTimestamp = System.currentTimeMillis();
									timerTime -= (curTimestamp - timerPrevTimestamp);
									timerPrevTimestamp = curTimestamp;
									
									if (timerTime < 0) {
										timerTime = 0;
										
										timerTimeup();
										timerThreadFlag = false;
										timerCountdownThread = null;
										handler.post(new Runnable() {
											@Override
											public void run() {
												btnTimerReset.setEnabled(true);
												btnTimerStartAndStop.setText("Start");
											}
										});
									}
									
									handler.post(new Runnable() {
										@Override
										public void run() {
											txtTimerTime.setText(elapsedTimeFormat.format(timerTime));
										}
									});
									
									
									try {
										Thread.sleep(50);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}

							private void timerTimeup() {
								// TODO Auto-generated method stub
								//Toast.makeText(getActivity(), "Timeup", Toast.LENGTH_LONG).show();
								if (toggleButtonTimerVibrator.isChecked()) {
									this.timerVibrate();
								} 
								
								if (toggleButtonTimerSound.isChecked()) {
									this.timerSound();
								} 
							}
							
							private void timerVibrate() {
								vibrator.vibrate(5000);
								
							}
							
							private void timerSound() {
								Intent intent = new Intent(getActivity(), TTSService.class);
								getActivity().startService(intent);
							}
						});
						timerCountdownThread.start();
					} else {
						timerThreadFlag = false;
						timerCountdownThread = null;
						btnTimerReset.setEnabled(true);
					}
				}
			});
			return rootView;
		}
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Log.d("onTabSelected", arg0.getText()+":"+arg0.getPosition());
		switch(arg0.getPosition()) {
		case 0:
			Fragment stopwatchFragment = new StopwatchFragment();
			ft.replace(R.id.container, stopwatchFragment);
			//ft.addToBackStack(null);
			ft.commit();
			break;
		case 1:
			Fragment timerFragment = new TimerFragment();
			ft.replace(R.id.container, timerFragment);
			//ft.addToBackStack(null);
			ft.commit();
			break;
		default:
			break;
		}
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

}
