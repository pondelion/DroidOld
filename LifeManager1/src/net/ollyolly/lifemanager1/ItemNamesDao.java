package net.ollyolly.lifemanager1;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class ItemNamesDao {

	public static final String TABLE_NAME = "item_names";
	static final String COLUMN_ID = "id";
	static final String COLUMN_NAME = "name";
	static final String COLUMN_USED = "used";	//0:�g�p���Ă��Ȃ�, 1:�g�p���Ă���;
	static final String[] COLUMNS = {COLUMN_ID, COLUMN_NAME, COLUMN_USED};
	public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" + 
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_USED + " INTEGER)"; 
	
	SQLiteDatabase db;
	
	public ItemNamesDao(SQLiteDatabase db){
		this.db = db;
		//Log.d("LifeManager1","ItemNamesDao() constractor");
		//add("��������");
	}
	
	public List<String> getItemNameList() {
		List<String> list = new ArrayList<String>();
		
		//Log.d("mytag", "getItemNameList, before query()");
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
		//Log.d("mytag", "getItemNameList, after query()");
		
		while (cursor.moveToNext()) {
			list.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
		}
		
		cursor.close();
		
		return list;
	}
	
	public List<String> getUsedItemNameList() {
		List<String> list = new ArrayList<String>();
		
		Cursor cursor = db.query(TABLE_NAME, null, COLUMN_USED + " = ?", new String[] {"1"}, null, null, null);
		
		while (cursor.moveToNext()) {
			list.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
		}
		
		cursor.close();
		
		return list;
	}
	
	public int getUsedItemCount() {
		List<String> itemNames = null;
		
		itemNames = getUsedItemNameList();
		
		if (itemNames != null) {
			return itemNames.size();
		}
		
		return 0;
	}
	
	public boolean exists(String name) {
		//Log.d("mytag", "exists(), before query()");
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, COLUMN_NAME + " = ?", new String[]{name}, null, null, null);
		//Log.d("mytag", "exists(), after query()");
		
		if (cursor.moveToFirst()) {
			cursor.close();
			return true;
		} else {
			cursor.close();
			return false;
		}
	}
	
	public void add(String name) {
		
		if (exists(name)) {	//COLUMN_NAME = name �̃��R�[�h�����ɑ��݂���ꍇ�͂��̃��R�[�h��COLUMN_USED��1�ɃZ�b�g���A�g�p����Ă���Ƃ����t���O�����Ă�
			//Toast.makeText(null, "���ɑ��݂��܂�", Toast.LENGTH_LONG).show();
			//Log.d("LifeManager1","ItemNamesDao add() exists");
			ContentValues cv = new ContentValues();
			cv.put(COLUMN_USED, 1);
			db.update(TABLE_NAME, cv, COLUMN_NAME + " = ?", new String[] {name});
			return;
		}
		//Log.d("LifeManager1","ItemNamesDao add() not exists");
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_USED, 1);
		
		db.insert(TABLE_NAME, null, values);
		
		//MainActivity.mDataDao.addNewColumn(name);

	}
	
	public void delete(String name) {
		
		if (!exists(name)) { //COLUMN_NAME = name�̃��R�[�h�����݂��Ȃ��ꍇ�͉������Ȃ�
			return;
		}
		//COLUMN_NAME = name�̃��R�[�h�����݂���ꍇ�́ACOLUMN_NAME = name�������R�[�h��COLUMN_USED=0�ɃZ�b�g���A�g�p����Ă��Ȃ��Ƃ����t���O�𗧂Ă�
		
		
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_USED, 0);
		db.update(TABLE_NAME, cv, COLUMN_NAME + " = ?", new String[] {name});
		
		//db.delete(TABLE_NAME, COLUMN_NAME + " = ?", new String[] {name});
	}
}
