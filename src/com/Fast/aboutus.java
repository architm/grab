package com.Fast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class aboutus extends Activity implements OnClickListener {
	Button bOK, bRegiter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

		bOK = (Button) findViewById(R.id.bOK);
		bRegiter = (Button) findViewById(R.id.bRegister);

		bOK.setOnClickListener(this);
		bRegiter.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bOK:
			finish();
			break;
		case R.id.bRegister:
			Intent i = new Intent("com.Fast.REGISTER");
			startActivity(i);
			break;
		}
	}
}
