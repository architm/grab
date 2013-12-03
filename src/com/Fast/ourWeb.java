package com.Fast;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ourWeb extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return super.shouldOverrideUrlLoading(view, url);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		// TODO Auto-generated method stub
		super.onPageFinished(view, url);
		// EditText br=th;
		// br.et.setText(url);

	}
}
