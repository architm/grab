package com.Fast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EncodingUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Bookmarks extends Activity implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {
	String classes[] = new String[100];
	List<String> book = new ArrayList<String>();
	List<String> urlList = new ArrayList<String>();
	Button bSend, bRetrieve, bLogin;
	String str = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bookmarks);
		BookmarkDB bdb = new BookmarkDB(this);
		Cursor c = bdb.retrieveBookmark();
		// int k=0;
		try {

			if (c != null) {
				if (c.moveToFirst()) {
					str += "<bookmark>";
					do {
						String title = c.getString(c.getColumnIndex("title"));
						String url = c.getString(c.getColumnIndex("url"));
						book.add(title + "\n" + url);
						urlList.add(url);
						str += "<item><title>" + title + "</title><url>" + url
								+ "</url></item>";
						// str+=classes[k];
						// k++;
					} while (c.moveToNext());
					str += "</bookmark>";
					// Toast.makeText(this,str, Toast.LENGTH_LONG).show();
					ListView books = (ListView) findViewById(R.id.listView1);
					books.setOnItemClickListener(this);
					books.setOnItemLongClickListener(this);
					books.setAdapter(new ArrayAdapter<String>(Bookmarks.this,
							android.R.layout.simple_list_item_1, book));
				}

			} else {
				Toast.makeText(this, "There are no Bookmarks to show...",
						Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "Unable to open bookmarks database...",
					Toast.LENGTH_SHORT).show();
		}

		// FULLSCREEN code
		/*
		 * requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */
		/*
		 * try{ ArrayAdapter<String> adapter; adapter=new
		 * ArrayAdapter<String>(Bookmarks.this,
		 * android.R.layout.simple_list_item_1, classes);
		 * adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		 * setListAdapter(adapter); //TextView
		 * tv=(TextView)findViewById(R.id.tvBook); //tv.setText(str);
		 * //Toast.makeText(this, classes[0], Toast.LENGTH_LONG).show(); }
		 * catch(Exception e){ String st=e.getMessage()+"\n"; for(int
		 * i=0;i<k;i++){ st+=classes[i]+"\n"; } Toast.makeText(this, st,
		 * Toast.LENGTH_LONG).show(); }
		 */
		bdb.close();
		c.close();
		bSend = (Button) findViewById(R.id.bSend);
		bRetrieve = (Button) findViewById(R.id.bRetrieve);
		bSend.setOnClickListener(this);
		bRetrieve.setOnClickListener(this);

	}

	EditText etUser, etPass, Ruser;
	AlertDialog ad, ad1;

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.bSend:
			/*
			 * Intent i = new Intent("com.Fast.SendBookmarks");
			 * startActivity(i);
			 */
			final AlertDialog.Builder alert = new AlertDialog.Builder(this);
			final LinearLayout layout = new LinearLayout(this);
			layout.setOrientation(LinearLayout.VERTICAL);
			alert.setTitle("Login for the bookmark...");
			final TextView t1 = new TextView(this);
			etUser = new EditText(this);
			final TextView t2 = new TextView(this);
			etPass = new EditText(this);
			etPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			// etTitle.setText(gTitle);
			// etUrl.setText(search.getText());
			t1.setText("Username");
			t2.setText("Password");
			final LinearLayout layouthori = new LinearLayout(this);
			layouthori.setOrientation(LinearLayout.HORIZONTAL);
			final Button ok = new Button(this);
			final Button cancel = new Button(this);
			ok.setText("Login");
			cancel.setText("Cancel");
			ok.setOnClickListener(this);
			cancel.setOnClickListener(this);

			layout.addView(t1);
			layout.addView(etUser);
			layout.addView(t2);
			layout.addView(etPass);
			layouthori.addView(ok);
			layouthori.addView(cancel);
			layout.addView(layouthori);
			alert.setView(layout);
			ok.setId(R.string.bLogin);
			cancel.setId(R.string.cancel);
			ad = alert.create();
			ad.show();

			break;

		case R.id.bRetrieve:
			try {
				final AlertDialog.Builder alrt = new AlertDialog.Builder(this);
				final LinearLayout lay = new LinearLayout(this);
				lay.setOrientation(LinearLayout.VERTICAL);
				alrt.setTitle("Login for the bookmark...");
				final TextView username = new TextView(this);
				Ruser = new EditText(this);

				username.setText("Username");
				final LinearLayout layouthor = new LinearLayout(this);
				layouthor.setOrientation(LinearLayout.HORIZONTAL);
				final Button ok1 = new Button(this);
				final Button cancel1 = new Button(this);
				ok1.setText("Retrieve");
				cancel1.setText("Cancel");
				ok1.setOnClickListener(this);
				cancel1.setOnClickListener(this);

				lay.addView(username);
				lay.addView(Ruser);
				layouthor.addView(ok1);
				layouthor.addView(cancel1);
				lay.addView(layouthor);
				alrt.setView(lay);
				ok1.setId(R.string.bRetrieve);
				cancel1.setId(R.string.bRCancel);
				ad1 = alrt.create();
				ad1.show();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case R.string.bLogin:
			WebView webView = new WebView(this);

			setContentView(webView);

			String url = "http://www.archit.vacau.com";
			String postData = "bookmarks=" + str + "&username="
					+ etUser.getText().toString() + "&password="
					+ etPass.getText().toString();
			// "bookmarks="+str+"&username="+etUser+"&password="+etPass
			// byte[] bb=postData.getBytes();
			webView.postUrl(url, EncodingUtils.getBytes(postData, "BASE64"));
			ad.dismiss();
			break;
		case R.string.cancel:
			ad.dismiss();
			break;
		case R.string.bRetrieve:
			try {
				executeHttpGet(Ruser.getText().toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ad1.dismiss();
			break;

		case R.string.bRCancel:
			ad1.dismiss();
			break;
		}
	}

	/*
	 * @Override protected void onListItemClick(ListView l, View v, int
	 * position, long id) { // TODO Auto-generated method stub
	 * super.onListItemClick(l, v, position, id); String selected =
	 * classes[position]; try { Class classSelect = Class.forName("com.Fast." +
	 * selected); Intent disp = new Intent(Bookmarks.this, classSelect);
	 * startActivity(disp); } catch (ClassNotFoundException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 * 
	 * }
	 */
	public void executeHttpGet(String user) throws Exception {
		BufferedReader in = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(
					"http://archit.vacau.com/user_bookmark_storage/" + user));
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			String page = sb.toString();
			// System.out.println(page);
			// Toast.makeText(this,page, Toast.LENGTH_LONG).show();
			try {

				// File fXmlFile = new File("c:\\file.xml");
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(new InputSource(
						new ByteArrayInputStream(page.getBytes("utf-8"))));
				doc.getDocumentElement().normalize();

				// System.out.println("Root element :" +
				// doc.getDocumentElement().getNodeName());
				NodeList nList = doc.getElementsByTagName("item");
				// System.out.println("-----------------------");
				String sd = "";
				int x = 1;
				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;

						String tt = getTagValue("title", eElement).toString();
						String ur = getTagValue("url", eElement).toString();
						BookmarkDB bdb = new BookmarkDB(this);

						try {
							bdb.addRow(tt, ur);

						} catch (Exception ex) {
							String problem = "Bookmark Adding failed" + "\n"
									+ ex.getMessage();
							Toast.makeText(this, problem, Toast.LENGTH_SHORT);
						}
						sd += x + ".) " + tt + "  " + ur + "\n";
						x++;
					}
				}
				Toast.makeText(this, "Bookmarks have been added!!!",
						Toast.LENGTH_SHORT).show();
				Toast.makeText(this, sd, Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();

		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// String selected = book.get(arg2);
		try {
			Class classSelect = Class.forName("com.Fast.FastBrowserActivity");
			Intent disp = new Intent(Bookmarks.this, classSelect);
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
			BookmarkDB bdb = new BookmarkDB(this);
			bdb.deleteBookmark(ur);
			bdb.close();
			startActivity(getIntent());
			Toast.makeText(this, "Bookmark Deleted: \n" + book.get(arg2),
					Toast.LENGTH_LONG).show();
			finish();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
