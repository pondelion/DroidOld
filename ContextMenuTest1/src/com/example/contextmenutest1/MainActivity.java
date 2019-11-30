package com.example.contextmenutest1;

import android.os.Bundle;
import android.app.Activity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

	private static final int BUTTON1_MENU_ITEM1 = Menu.FIRST;
	private static final int BUTTON1_MENU_ITEM2 = Menu.FIRST + 1;

	private static final int BUTTON2_MENU_ITEM1 = Menu.FIRST + 2;;
	private static final int BUTTON2_MENU_ITEM2 = Menu.FIRST + 3;

	private Button button1, button2;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);
		
		button1 = new Button(this);
		button1.setText("ボタン１");
		layout.addView(button1);
		registerForContextMenu(button1);
		
		button2 = new Button(this);
		button2.setText("ボタン2");
		layout.addView(button2);
		registerForContextMenu(button2);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.equals(button1)) {
			menu.add(Menu.NONE, BUTTON1_MENU_ITEM1, Menu.NONE, "Button1Menu1");
			menu.add(Menu.NONE, BUTTON1_MENU_ITEM2, Menu.NONE, "Button1Menu2");
		} else if (v.equals(button2)) {
			menu.add(Menu.NONE, BUTTON2_MENU_ITEM1, Menu.NONE, "Button2Menu1");
			menu.add(Menu.NONE, BUTTON2_MENU_ITEM2, Menu.NONE, "Button2Menu2");
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case BUTTON1_MENU_ITEM1:
			// ここにBUTTON1_MENU_ITEM1が押された時の処理を記述する。
			button1.setText(String.format("menuTitle=%s", item.getTitle()));
			return true;
		case BUTTON1_MENU_ITEM2:
			button1.setText(String.format("menuTitle=%s", item.getTitle()));
			// ここにBUTTON1_MENU_ITEM2が押された時の処理を記述する。
			return true;
		case BUTTON2_MENU_ITEM1:
			// ここにBUTTON2_MENU_ITEM1が押された時の処理を記述する。
			button2.setText(String.format("menuTitle=%s", item.getTitle()));
			return true;
		case BUTTON2_MENU_ITEM2:
			button2.setText(String.format("menuTitle=%s", item.getTitle()));
			// ここにBUTTON2_MENU_ITEM2が押された時の処理を記述する。
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

}
