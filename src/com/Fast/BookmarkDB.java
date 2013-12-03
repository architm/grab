package com.Fast;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class BookmarkDB {

	// the Activity or Application that is creating an object from this class.
	Context context;

	// a reference to the database used by this application/object
	private SQLiteDatabase db;

	// These constants are specific to the database. They should be
	// changed to suit your needs.
	private final String DB_NAME = "DB_BOOKMARK";
	private final int DB_VERSION = 1;

	// These constants are specific to the database table. They should be
	// changed to suit your needs.
	private final String TABLE_NAME = "BOOKMARKS";
	// private final String TABLE_ROW_ID = "id";
	private final String TABLE_ROW_ONE = "title";
	private final String TABLE_ROW_TWO = "url";

	public BookmarkDB(Context context) {
		this.context = context;

		// create or open the database
		CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
		this.db = helper.getWritableDatabase();
	}

	/**********************************************************************
	 * ADDING A ROW TO THE DATABASE TABLE
	 * 
	 * This is an example of how to add a row to a database table using this
	 * class. You should edit this method to suit your needs.
	 * 
	 * the key is automatically assigned by the database
	 * 
	 * @param rowStringOne
	 *            the value for the row's first column
	 * @param rowStringTwo
	 *            the value for the row's second column
	 */
	public void addRow(String rowStringOne, String rowStringTwo)
			throws SQLiteException {
		// this is a key value pair holder used by android's SQLite functions
		ContentValues values = new ContentValues();
		values.put(TABLE_ROW_ONE, rowStringOne);
		values.put(TABLE_ROW_TWO, rowStringTwo);

		// ask the database object to insert the new data
		try {
			db.insert(TABLE_NAME, null, values);
			// Toast.makeText(this.context,rowStringOne+"   "+rowStringTwo+"\n",
			// Toast.LENGTH_LONG).show();
			// Toast.makeText(this.context, "i think they are added",
			// Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			/*
			 * Log.e("DB ERROR", e.toString()); e.printStackTrace();
			 */
			Toast.makeText(this.context, "mission failed :)",
					Toast.LENGTH_SHORT).show();
		} finally {
			db.close();
		}
	}

	public Cursor retrieveBookmark() {
		// return db.query(TABLE_NAME, new String[]
		// {TABLE_ROW_ONE,TABLE_ROW_TWO}, null, null, null, null, null);
		Cursor c = db.rawQuery("SELECT title,url FROM " + TABLE_NAME, null);
		return c;
	}

	public Cursor deleteBookmark(String url) {
		// return db.query(TABLE_NAME, new String[]
		// {TABLE_ROW_ONE,TABLE_ROW_TWO}, null, null, null, null, null);
		String whereClause = TABLE_ROW_TWO + " = " + "'" + url + "'";
		db.delete(TABLE_NAME, whereClause, null);
		// Cursor c =
		// db.rawQuery("DELETE FROM "+TABLE_NAME+" WHERE "+TABLE_ROW_TWO+" = "+"'"+url+"'"
		// , null);
		// return c;
		return null;
	}

	public void close() {
		db.close();
	}

	private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
		public CustomSQLiteOpenHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// This string is used to create the database. It should
			// be changed to suit your needs.
			String newTableQueryString = "create table if not exists "
					+ TABLE_NAME + " (" + TABLE_ROW_ONE + " text,"
					+ TABLE_ROW_TWO + " text" + ");";
			// execute the query string to the database.
			db.execSQL(newTableQueryString);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// NOTHING TO DO HERE. THIS IS THE ORIGINAL DATABASE VERSION.
			// OTHERWISE, YOU WOULD SPECIFIY HOW TO UPGRADE THE DATABASE.
		}
	}

}