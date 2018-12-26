package com.flocater;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	public static final int DB_VERSION = 1;
	public static final String DB_NAME = "friendfinder.db";
   
	public DBHelper(Context ctx) {
		super(ctx, DB_NAME, null, DB_VERSION);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
          createTables(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}
	
	public void createTables(SQLiteDatabase database) {
		String subscriber_table_sql = "create table " + Database.SUBSCRIBE_TABLE_NAME + " ( " +
				Database.SUBSCRIBE_ID 	+ " integer  primary key autoincrement," + 
				Database.SUBSCRIBE_NAME + " TEXT," +
				Database.SUBSCRIBE_PHONE + " TEXT," +
				Database.SUBSCRIBE_DEVICEID  + " TEXT," +
				Database.SUBSCRIBE_AREA + " TEXT," +
				Database.SUBSCRIBE_CITY + " TEXT)";
		
		
		String friends_table_sql = "create table " + Database.FRIENDS_TABLE_NAME + " ( " +
				Database.FRIENDS_ID 	+ " integer  primary key autoincrement," + 
				Database.FRIENDS_NAME + " TEXT," +
				Database.FRIENDS_PHONE + " TEXT," +
				Database.FRIENDS_AREA+ " TEXT," +
				Database.FRIENDS_CITY + " TEXT)";
		
        try {
		   database.execSQL(subscriber_table_sql);
		   database.execSQL(friends_table_sql); 
		   Log.d("SUBSCRIBE","Tables created!");
		   
        }
        catch(Exception ex) {
        	Log.d("SUBSCRIBE", "Error in DBHelper.onCreate() : " + ex.getMessage());
        }
	}

}

