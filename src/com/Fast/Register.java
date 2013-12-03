package com.Fast;

import java.util.UUID;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener {
	EditText et1, et2, et3, et4;
	Button bReg, bCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		bReg = (Button) findViewById(R.id.bReg);
		bCancel = (Button) findViewById(R.id.bCancel);
		bReg.setOnClickListener(this);
		bCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bReg:
			et1 = (EditText) findViewById(R.id.et1);
			et2 = (EditText) findViewById(R.id.et2);
			et3 = (EditText) findViewById(R.id.et3);
			et4 = (EditText) findViewById(R.id.et4);

			String dev = deviceId();

			if (et1.getText().toString().equals("")
					|| et2.getText().toString().equals("")
					|| et3.getText().toString().equals("")
					|| et4.getText().toString().equals("")) {
				Toast.makeText(this,
						"None of the entries can be left blank!!!",
						Toast.LENGTH_SHORT).show();
			} else if (et2.getText().toString()
					.equals(et3.getText().toString())) {
				WebView webView = new WebView(this);

				String url = "http://www.archit.vacau.com/signup.php";
				String postData = "username=" + et1.getText().toString()
						+ "&password=" + et2.getText().toString()
						+ "&security=" + et4.getText().toString()
						+ "&confirm_password=" + et3.getText().toString()
						+ "&device_id=" + dev.toString();
				Toast.makeText(
						this,
						"You have been registered with a phone mac-id:\n" + dev,
						Toast.LENGTH_SHORT).show();
				webView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
				finish();
			} else {
				Toast.makeText(
						this,
						"The Password doesn't match!!!"
								+ et1.getText().toString() + ""
								+ et2.getText().toString() + ""
								+ et3.getText().toString() + ""
								+ et4.getText().toString(), Toast.LENGTH_SHORT)
						.show();
			}
			break;

		case R.id.bCancel:
			finish();
			break;
		}

	}

	private String deviceId() {
		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String device = deviceUuid.toString();
		return device;
	}
}
