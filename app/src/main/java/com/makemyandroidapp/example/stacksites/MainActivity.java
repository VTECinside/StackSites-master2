package com.makemyandroidapp.example.stacksites;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	private SitesAdapter mAdapter;
	private ListView sitesList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("StackSites", "OnCreate()");
		setContentView(R.layout.activity_main);

		//Get reference to our ListView
		sitesList = (ListView)findViewById(R.id.sitesList);
		
		//Set the click listener to launch the browser when a row is clicked.
		sitesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				String url = mAdapter.getItem(pos).getLink();
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
				
			}
			
		});


		/*
		 * If network is available download the xml from the Internet.
		 * If not then try to use the local file from last time.
		 */
		if(isNetworkAvailable()){
			Log.i("StackSites", "starting download Task");
			SitesDownloadTask download = new SitesDownloadTask();
			download.execute();
		}else{
			mAdapter = new SitesAdapter(getApplicationContext(), -1, SitesXmlPullParser.getStackSitesFromFile(MainActivity.this));
			sitesList.setAdapter(mAdapter);
		}


	}






	
	//Helper method to determine if Internet connection is available.
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	} 
	
	/*
	 * AsyncTask that will download the xml file for us and store it locally.
	 * After the download is done we'll parse the local file.
	 */
	private class SitesDownloadTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {

            Intent intent = getIntent();
            String text  = intent.getStringExtra("text");

			Pattern p = Pattern.compile("http://m\\.ebay");
            Matcher m = p.matcher(text);
            text = m.replaceFirst("http://www.ebay");

            text = text + "&_rss=1";
            /*
			StringBuffer temp = new StringBuffer(text);
			temp.append("&_rss=1");
			*/

			//Download the file

            /*
            text = text.replaceAll("&", "&amp;");
            text = text.replaceAll("\\?", "&#63;");
            Log.i("info", text.toString());
            */

			try {
				Downloader.DownloadFromUrl(text, openFileOutput("StackSites.xml", Context.MODE_PRIVATE));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result){
			//setup our Adapter and set it to the ListView.
			mAdapter = new SitesAdapter(MainActivity.this, -1, SitesXmlPullParser.getStackSitesFromFile(MainActivity.this));
			sitesList.setAdapter(mAdapter);
			Log.i("StackSites", "adapter size = "+ mAdapter.getCount());
		}
	}

}
