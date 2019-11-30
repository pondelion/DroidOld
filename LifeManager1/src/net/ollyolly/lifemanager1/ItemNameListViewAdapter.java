package net.ollyolly.lifemanager1;

import java.util.List;

import net.ollyolly.lifemanager1.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ItemNameListViewAdapter extends ArrayAdapter<String> implements
		ListAdapter {

	private LayoutInflater layoutInflater;
	private ItemNamesDao mItemNamesDao;
	private ItemSettingActivity mItemSettingActivity;
	
	public ItemNameListViewAdapter(Context context,List<String> objects, ItemNamesDao itemNamesDao) {
		super(context,-1, objects);
		// TODO Auto-generated constructor stub
		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mItemSettingActivity = (ItemSettingActivity)context;
		mItemNamesDao = itemNamesDao;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.item_name_list, null);
		}
		
		String itemName = (String)getItem(position);
		TextView textViewItemName 	= (TextView)convertView.findViewById(R.id.textViewItemName);
		textViewItemName.setText(itemName);
		
		Button buttonDeleteItem 	= (Button)convertView.findViewById(R.id.buttonItemDelete);
		buttonDeleteItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				deleteItem(((TextView)((View)v.getParent()).findViewById(R.id.textViewItemName)).getText().toString());
			}
		});
		
		return convertView;
	}
	
	private void deleteItem(final String itemName) {
		
		//Toast.makeText(getContext(), itemName + "を削除します", Toast.LENGTH_LONG).show();
		AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
		dialog.setTitle(itemName +"を削除します。よろしいですか？※この項目を持つ過去のデータも削除されます。");
		dialog.setPositiveButton("削除", new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            	mItemNamesDao.delete(itemName);
            	mItemSettingActivity.refreshItemList();
            }
        });
		dialog.setNegativeButton("キャンセル",new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        });
		dialog.show();
	}

}
