package net.ollyolly.lifemanager1;

import java.util.ArrayList;
import java.util.List;

import net.ollyolly.lifemanager1.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ItemSettingActivity extends Activity {

	
	private ListView mListViewItemNames;
	private ItemNameListViewAdapter mItemNameListViewAdapter;
	private Button mBtnAddItem;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.item_setting);
		
		mListViewItemNames = (ListView)findViewById(R.id.listViewItemNames);
		//Log.d("mytag", "onCreate, before getItemNameList()");
		
		mItemNameListViewAdapter = new ItemNameListViewAdapter(this, MainActivity.mItemNamesDao.getUsedItemNameList(), MainActivity.mItemNamesDao);
		//Log.d("mytag", "onCreate, after getItemNameList()");
		mListViewItemNames.setAdapter(mItemNameListViewAdapter);
		
		
		mBtnAddItem = (Button)findViewById(R.id.buttonAddItem);
		
		mBtnAddItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addItem();
			}
		});
		
	}
	
	public void refreshItemList() {
		mItemNameListViewAdapter.clear();
		List<String> itemList = MainActivity.mItemNamesDao.getUsedItemNameList();
		for (String item : itemList) {
			mItemNameListViewAdapter.add(item);
		}
	}
	
	private void addItem() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("í«â¡Ç∑ÇÈçÄñ⁄ñºÇâpêîéöÇ≈ì¸óÕÇµÇƒÇ≠ÇæÇ≥Ç¢");
        LayoutInflater inflater = LayoutInflater.from(this);
        final View layout = inflater.inflate(R.layout.dialog_item_add, null);
        dialog.setView(layout);
        dialog.setPositiveButton("í«â¡", new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            	EditText editTextNewItemName = (EditText)layout.findViewById(R.id.editTextNewItemName);
            	MainActivity.mItemNamesDao.add(editTextNewItemName.getText().toString());
            	MainActivity.mDataDao.addNewColumn(editTextNewItemName.getText().toString());
            	refreshItemList();
            }
        });
		dialog.setNegativeButton("ÉLÉÉÉìÉZÉã",new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });
		dialog.show();
	}
}
