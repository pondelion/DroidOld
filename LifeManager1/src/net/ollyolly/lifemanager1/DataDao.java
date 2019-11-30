package net.ollyolly.lifemanager1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.widget.Toast;

public class DataDao {

	public static final String TABLE_NAME = "data";
	//static final String COLUMN_ID = "id";
	static final String COLUMN_DATE = "date";
	static final String[] COLUMNS = /*{COLUMN_ID,*/{ COLUMN_DATE};
	public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" + 
			COLUMN_DATE + " INTEGER PRIMARY KEY)"; 
	
	SQLiteDatabase db;
	
	public DataDao(SQLiteDatabase db){
		this.db = db;
		
	}
	
	public void addNewColumn(String item_name) {
		//db.execSQL("alter table " + TABLE_NAME + " add " + item_name + " TEXT");
		//MainActivity.messageBox("addNewColumn() is caleld");
		db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + item_name + "_start INTEGER DEFAULT -1");
		db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + item_name + "_end INTEGER DEFAULT -1");
		
	}
	
	public void saveData(int year, int month, int day, List<Data> dataList) {
		
		Calendar cal = new GregorianCalendar(year, month-1, day);
		Date date = cal.getTime();
		
		ContentValues cv = new ContentValues();
		
		//MainActivity.messageBox("saveData():date.gatTime()=" + String.valueOf(date.getTime()));
		
		
		int updateNum = 0;
		for (Data data : dataList) {
			int startTime = data.startTime;
			int endTime = data.endTime;
			
			//MainActivity.messageBox("saveData():data.startTime=" + String.valueOf(data.startTime));
			//MainActivity.messageBox("saveData():data.endTime=" + String.valueOf(data.endTime));
			
			if (startTime >= 0) {
				//MainActivity.messageBox("saveData():data.itemName=" + data.itemName);
				cv.put(data.itemName + "_start", startTime);
				updateNum++;
				
			}
			if (endTime >= 0) {
				cv.put(data.itemName + "_end", endTime);
				updateNum++;
			}
			
		}
		
		if (checkDataExistence(year, month, day)) { //すでに指定の日付のデータがDBに存在する場合はデータをアップデート
			
			//MainActivity.messageBox("saveData(), 指定日付のすでにﾃﾞｰﾀが存在");
				
			if (updateNum > 0) {
				
				db.update(TABLE_NAME, cv, COLUMN_DATE + " = ?", new String[] { String.valueOf(date.getTime())});
				MainActivity.messageBox("データをセーブしました");
			}
		} else { //指定の日付のデータがDBに存在しない場合は新しく追加
			
					// TODO Auto-generated method stub
			//MainActivity.messageBox("saveData(), 指定日付のﾃﾞｰﾀが存在しない");
				
			cv.put(COLUMN_DATE, date.getTime());
			if(db.insert(TABLE_NAME, null, cv) < 0) {
				MainActivity.messageBox("SQLiteDatabase:insert() failed");;
			} else {
				MainActivity.messageBox("新しくデータを追加しました");
			};
		}
	}
	
	public boolean checkDataExistence(int year, int month, int day) {
		/*
		if(getAllData(year, month, day) == null)
		{
			MainActivity.messageBox("checkDataExistence();, getAllData() == null");
		} else {
			MainActivity.messageBox("checkDataExistence();, getAllData() != null");
		}
		*/
		return (getAllData(year, month, day) != null);
	}
	
	public List<Data> getAllData(int year, int month, int day) { //指定日のすべての項目に関する開始時間・終了時間のデータリストを返す
		List<Data> dataList = null;
		
		Calendar cal = new GregorianCalendar(year, month-1, day);
		Date date = cal.getTime();
		//MainActivity.messageBox("getAllData():date.getTime()=" + String.valueOf(date.getTime()));
		Cursor cursor = db.query(TABLE_NAME, null, COLUMN_DATE + " = ?", new String[] { String.valueOf(date.getTime()) }, null, null, COLUMN_DATE);
		
		if (cursor.moveToFirst()) {
			
			dataList = new ArrayList<Data>();
			for (String itemName : MainActivity.mItemNamesDao.getUsedItemNameList()) {
				Data data = new Data();
				data.itemName = itemName;
				data.startTime = cursor.getInt(cursor.getColumnIndex(itemName + "_start"));
				data.endTime = cursor.getInt(cursor.getColumnIndex(itemName + "_end"));
				dataList.add(data);
			}
		}
		
		cursor.close();
		
		return dataList;
	}
	
	public List<Data> getAllDataOfSpecifiedItemAndPeriod(String itemName, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
		List<Data> dataList = null;
		
		Calendar startCal = new GregorianCalendar(startYear, startMonth-1, startDay);
		Date startDate = startCal.getTime();
		Calendar endCal = new GregorianCalendar(endYear, endMonth-1, endDay);
		Date endDate = endCal.getTime();
		
		Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_DATE, itemName + "_start", itemName + "_end"}, COLUMN_DATE + " >= ? and " + COLUMN_DATE + " <= ?", new String[] { String.valueOf(startDate.getTime()), String.valueOf(endDate.getTime()) }, null, null, COLUMN_DATE);
		
		if (cursor.moveToFirst()) {
			dataList = new ArrayList<Data>();
			
			do {
				Data data = new Data();
				data.itemName	= itemName;
				data.date		= cursor.getLong(cursor.getColumnIndex(COLUMN_DATE));
				data.startTime	= cursor.getInt(cursor.getColumnIndex(itemName+"_start"));
				data.endTime	= cursor.getInt(cursor.getColumnIndex(itemName + "_end"));
				dataList.add(data);
			} while (cursor.moveToNext());
			
		}
		
		cursor.close();
		return dataList;
	}
	
	public double getAverageValue(String itemName, StatisticalInformationActivity.MODE mode, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
		if (mode == StatisticalInformationActivity.MODE.START_AND_END) {
			return -1.0;
		}
		
		List<Data> dataList = getAllDataOfSpecifiedItemAndPeriod(itemName, startYear, startMonth, startDay, endYear, endMonth, endDay);
		if (dataList == null) {
			return -1.0;
		}
		
		double averageValue = 0.0;
		
		switch (mode) {
		case START:
			for(Data data : dataList) {
				averageValue += data.startTime;
			}
			averageValue /= (dataList.size()*60.0);
			break;
		case END:
			for(Data data : dataList) {
				averageValue += data.endTime;
			}
			averageValue /= (dataList.size()*60.0);
			break;
		case END_MINUS_START:
			for(Data data : dataList) {
				averageValue += (data.endTime - data.startTime);
			}
			averageValue /= (dataList.size()*60.0);
			break;
		default:
			averageValue = -1.0;
		}
		
		return averageValue;
	}
}
