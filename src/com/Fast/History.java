package com.Fast;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class History extends Activity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {
	String classes[] = new String[100];
	List<String> book = new ArrayList<String>();
	List<String> urlList = new ArrayList<String>();
	Button bClear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		HistoryDB bdb = new HistoryDB(this);
		Cursor c = bdb.retrieveHistory();
		// int k=0;
		try {

			if (c != null) {
				if (c.moveToFirst()) {
					do {
						String title = c.getString(c.getColumnIndex("name"));
						String url = c.getString(c.getColumnIndex("link"));
						book.add(title + "\n" + url);
						urlList.add(url);

					} while (c.moveToNext());
					// Toast.makeText(this,str, Toast.LENGTH_LONG).show();
					ListView books = (ListView) findViewById(R.id.lvHistory);
					books.setOnItemClickListener(this);
					books.setOnItemLongClickListener(this);
					books.setAdapter(new ArrayAdapter<String>(History.this,
							android.R.layout.simple_list_item_1, book));
				}

			} else {
				Toast.makeText(this, "There are no History to show...",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "Unable to open History database...",
					Toast.LENGTH_SHORT).show();
		}
		bdb.close();
		c.close();
		bClear = (Button) findViewById(R.id.bClear);
		bClear.setOnClickListener(this);

	}

	EditText etUser, etPass, Ruser;
	AlertDialog ad, ad1;

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bClear:
			HistoryDB bdb = new HistoryDB(this);
			bdb.clearHistory();
			bdb.close();
			Toast.makeText(this, "History Cleared!!!", Toast.LENGTH_SHORT)
					.show();
			finish();
			startActivity(getIntent());
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// String selected = book.get(arg2);
		try {
			Class classSelect = Class.forName("com.Fast.FastBrowserActivity");
			Intent disp = new Intent(History.this, classSelect);
			String ur = urlList.get(arg2);
			Bundle ans = new Bundle();
			ans.putString("urlSelected", ur);
			disp.putExtras(ans);
			startActivity(disp);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		try {
			String ur;
			ur = urlList.get(arg2);
			HistoryDB bdb = new HistoryDB(this);
			bdb.deleteHistory(ur);
			bdb.close();
			startActivity(getIntent());
			Toast.makeText(this, "HyperLink Deleted: \n" + book.get(arg2),
					Toast.LENGTH_LONG).show();
			finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
