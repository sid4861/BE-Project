package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SQLiteAdapter {

	public static final String MYDATABASE_NAME = "Home Automation";

	public static final int MYDATABASE_VERSION = 1;


	private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;

	private Context context;
	private final ArrayList<User> Userlist = new ArrayList<User>();
	private final ArrayList<History> Historylist = new ArrayList<History>();

    
	public SQLiteAdapter(Context c) {
		context = c;
	}

	public SQLiteAdapter openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
				MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;
	}

	public SQLiteAdapter openToWrite() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null,
				MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		sqLiteHelper.close();
	}

	//db.execSQL("create table History(id Integer ,lampname text,time text,status text);");
	public long insertHistory(String lampname, String time, String status) {

		ContentValues contentValues = new ContentValues();


		contentValues.put("lampname", lampname);
		contentValues.put("time", time);
		contentValues.put("status", status);


		return sqLiteDatabase.insert("History", null, contentValues);
	}

	public long insertUser(String name, String username, String password, String strMobile1) {

		ContentValues contentValues = new ContentValues();


		contentValues.put("name", name);
		contentValues.put("username", username);
		contentValues.put("password", password);

		contentValues.put("strMobile1", strMobile1);





		return sqLiteDatabase.insert("User", null, contentValues);
	}


	public long insertStatus(String id, String status) {

		ContentValues contentValues = new ContentValues();

		contentValues.put("id", id);
		contentValues.put("status", status);


		return sqLiteDatabase.insert("Status", null, contentValues);
	}

	//db.execSQL("create table Temp(id Integer ,tempvalue text);");
	public long insertTemp(String id, String tempvalue) {

		ContentValues contentValues = new ContentValues();

		contentValues.put("id", id);
		contentValues.put("tempvalue", tempvalue);


		return sqLiteDatabase.insert("Temp", null, contentValues);
	}



	public String getLight1Status() {

		String USerID = "-1";
		try {
			Cursor cursor = sqLiteDatabase.rawQuery(
					"select Status from Status where id=1;", null);
			if (cursor != null && cursor.moveToFirst()) {
				USerID =cursor.getString(0); // The 0 is the column index, we only
				// have 1 column, so the index is 0
			}

			return USerID;
		} catch (SQLiteException e) {
			//Log.e(TAG, "Get Last" + e);
			return "0";
		} finally {
			//Log.i(TAG, "Last User Id=" + USerID);

		}
	}


	public String getLight2Status() {

		String USerID = "-1";
		try {
			Cursor cursor = sqLiteDatabase.rawQuery(
					"select Status from Status where id=2;", null);
			if (cursor != null && cursor.moveToFirst()) {
				USerID =cursor.getString(0); // The 0 is the column index, we only
				// have 1 column, so the index is 0
			}

			return USerID;
		} catch (SQLiteException e) {
			//Log.e(TAG, "Get Last" + e);
			return "0";
		} finally {
			//Log.i(TAG, "Last User Id=" + USerID);

		}
	}


	public String getLight4Status() {

		String USerID = "-1";
		try {
			Cursor cursor = sqLiteDatabase.rawQuery(
					"select Status from Status where id=4;", null);
			if (cursor != null && cursor.moveToFirst()) {
				USerID =cursor.getString(0); // The 0 is the column index, we only
				// have 1 column, so the index is 0
			}

			return USerID;
		} catch (SQLiteException e) {
			//Log.e(TAG, "Get Last" + e);
			return "0";
		} finally {
			//Log.i(TAG, "Last User Id=" + USerID);

		}
	}
	//db.execSQL("create table History(id Integer ,lampname text,time text,status text);");

	public ArrayList<History> retrieveAllHistorylist() {


		Historylist.clear();
		Cursor cursor = sqLiteDatabase.rawQuery(
				"select * from History ORDER BY id DESC  ;", null);
		try {
			if (cursor.moveToFirst()) {
				do {
					History contact = new History();


					contact.setId(Integer.parseInt(cursor.getString(0)));
					contact.setLampname(cursor.getString(1));
					contact.setTime(cursor.getString(2));
					contact.setStatus(cursor.getString(3));
					Historylist.add(contact);
					//  consumer_metadata_details.add(field_type1);
				} while (cursor.moveToNext());
			}

			// return contact list
			cursor.close();
			//  db.close();
			return     Historylist;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("all_contact", "" + e);
		}finally {
			cursor.close();
		}

		return     Historylist;
	}




	public ArrayList<User> retrieveAllUser() {


		Userlist.clear();
		Cursor cursor = sqLiteDatabase.rawQuery(
				"select * from User;", null);
		try {
			if (cursor.moveToFirst()) {
				do {
					User contact = new User();


					contact.setId(Integer.parseInt(cursor.getString(0)));
					contact.setName(cursor.getString(1));
					contact.setUsername(cursor.getString(2));
					contact.setPassword(cursor.getString(3));
					contact.setMobNumber1(cursor.getString(4));
					Userlist.add(contact);
					//  consumer_metadata_details.add(field_type1);
				} while (cursor.moveToNext());
			}

			// return contact list
			cursor.close();
			//  db.close();
			return     Userlist;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("all_contact", "" + e);
		}finally {
			cursor.close();
		}

		return     Userlist;
	}

	public int Get_Total_LoginStatus() {


		Cursor cursor = sqLiteDatabase.rawQuery(
				"select * from LoginStatus;", null);
		int count = cursor.getCount();
		cursor.close();

		return count;
	}
	public int deleteLoginStatus() {

		int k = sqLiteDatabase.delete("LoginStatus", null, null);
		return k;
	}

	public int deletehistory() {

		int k = sqLiteDatabase.delete("History", null, null);
		return k;
	}
	public long insertLoginStatus(int id,String status) {

		ContentValues contentValues = new ContentValues();


		contentValues.put("id", id);
		contentValues.put("status", status);






		return sqLiteDatabase.insert("LoginStatus", null, contentValues);
	}
	public int Get_Total_User() {


		Cursor cursor = sqLiteDatabase.rawQuery(
				"select * from User;", null);
		int count = cursor.getCount();
		cursor.close();

		return count;
	}



	public String getLight3Status() {

		String USerID = "-1";
		try {
			Cursor cursor = sqLiteDatabase.rawQuery(
					"select Status from Status where id=3;", null);
			if (cursor != null && cursor.moveToFirst()) {
				USerID =cursor.getString(0); // The 0 is the column index, we only
				// have 1 column, so the index is 0
			}

			return USerID;
		} catch (SQLiteException e) {
			//Log.e(TAG, "Get Last" + e);
			return "0";
		} finally {
			//Log.i(TAG, "Last User Id=" + USerID);

		}
	}

	//db.execSQL("create table Temp(id Integer ,tempvalue text);");
	public String getTempStatus() {

		String USerID = "-1";
		try {
			Cursor cursor = sqLiteDatabase.rawQuery(
					"select tempvalue from Temp where id=1;", null);
			if (cursor != null && cursor.moveToFirst()) {
				USerID =cursor.getString(0); // The 0 is the column index, we only
				// have 1 column, so the index is 0
			}

			return USerID;
		} catch (SQLiteException e) {
			//Log.e(TAG, "Get Last" + e);
			return "0";
		} finally {
			//Log.i(TAG, "Last User Id=" + USerID);

		}
	}



	//db.execSQL("create table Temp(id Integer ,tempvalue text);");

	public void update_Temp(int id,String tempvalue) {
		// TODO Auto-generated method stub

		sqLiteDatabase.execSQL("update Temp set tempvalue='" + tempvalue + "'  where id="+id);
		//

	}


	public void update_light(int id,String status) {
		// TODO Auto-generated method stub

		sqLiteDatabase.execSQL("update status set Status='" + status + "'  where id="+id);
		//

	}


	
	public class SQLiteHelper extends SQLiteOpenHelper {
		/*
		 * Constructor called its super class
		 */
		public SQLiteHelper(Context context, String name,
							CursorFactory factory, int version) {
			super(context, name, factory, version);
		}



		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table User(id Integer PRIMARY KEY AUTOINCREMENT,name text,username text,password text,strMobile1 text);");

			db.execSQL("create table Status(id Integer ,status text);");
			db.execSQL("create table LoginStatus(id Integer ,status text);");
			db.execSQL("create table History(id INTEGER PRIMARY KEY AUTOINCREMENT ,lampname text,time text,status text);");

			Log.d("Log", "Database Created");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		}
		@Override
		public synchronized void close() {
		    if(sqLiteDatabase != null){
		    	sqLiteDatabase.close();
		    super.close();
		    }   
		}
	}

}