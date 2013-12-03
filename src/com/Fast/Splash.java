package com.Fast;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
//import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class Splash extends Activity {
	// MediaPlayer startup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		/*
		 * startup=MediaPlayer.create(Splash.this, R.raw.song); startup.start();
		 */
		Thread timer = new Thread() {
			public void run() {
				try {
					TextView tv = (TextView) findViewById(R.id.tv1);
					SharedPreferences gotPrefs = PreferenceManager
							.getDefaultSharedPreferences(getBaseContext());
					String pref = gotPrefs.getString("start", "User");// 500 is
																		// default
																		// value
					tv.setText("Welcome " + pref);
					tv.setBackgroundColor(Color.BLUE);
					sleep(1000);
				} catch (InterruptedException ix) {
					ix.printStackTrace();
				} finally {
					Intent startCounterActivity = new Intent(
							"com.Fast.FASTBROWSERACTIVITY");
					startActivity(startCounterActivity);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// startup.release();
		finish();
	}

}
