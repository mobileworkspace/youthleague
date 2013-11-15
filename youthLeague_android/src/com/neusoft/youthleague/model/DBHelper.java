package com.neusoft.youthleague.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = "DBHelper";

	private static final String DATABASE_NAME = "youth_league.db";
	private static final int DATABASE_VERSION = 1;

	private static DBHelper singleDbHelper;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static DBHelper getInstance(Context context) {
		if (singleDbHelper == null) {
			singleDbHelper = new DBHelper(context);
		}
		return singleDbHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub

		Log.d(TAG, "开始创建数据库中的表！");

		database.beginTransaction();

		try {

			/* 创建部门表 department */
			database.execSQL("CREATE TABLE IF NOT EXISTS department " + "("
					+ " id INTEGER PRIMARY KEY NOT NULL,"
					+ " name text NOT NULL, note TEXT " + ")");

			/* 创建职位表 position */
			database.execSQL("CREATE TABLE IF NOT EXISTS position_ " + "("
					+ " id INTEGER PRIMARY KEY ,"
					+ " name TEXT NOT NULL, note TEXT " + ")");

			/* 创建机构表 organization */
			database.execSQL("CREATE TABLE IF NOT EXISTS organization " + "("
					+ " id INTEGER PRIMARY KEY  NOT NULL,"
					+ " name TEXT NOT NULL, address TEXT,"
					+ " super_id INTEGER NOT NULL " + ")");

			/* 创建员工表 staff */
			database.execSQL("CREATE TABLE IF NOT EXISTS staff " + "("
					+ " id INTEGER PRIMARY KEY ,"
					+ " name TEXT NOT NULL, mobile TEXT, password_ TEXT, "
					+ " phone TEXT,"
					+ " organization_id INTEGER NOT NULL,"
					+ " department_id INTEGER , position_id INTEGER ,  "
					+ " is_administrator TEXT defaut '0', is_leader TEXT defaut '0', is_hipe TEXT default '0', "
					+ " is_departure TEXT default '0', is_warrant TEXT default '0'" + ")");
			
			database.setTransactionSuccessful();

			Log.d(TAG, "结束创建数据库中的表！");

		} finally {
			database.endTransaction();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
