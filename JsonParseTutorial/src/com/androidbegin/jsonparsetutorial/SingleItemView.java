package com.androidbegin.jsonparsetutorial;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleItemView extends Activity {
	// Declare Variables 
	String rank;
	String country;
	String population;
	String flag;
	ProgressDialog mProgressDialog;
	Bitmap bmImg = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singleitemview);
		// Execute loadSingleView AsyncTask
		new loadSingleView().execute();
	}

	public class loadSingleView extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(SingleItemView.this);
			// Set progressdialog title
			mProgressDialog.setTitle("Android JSON Parse Tutorial");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();

		}

		@Override
		protected String doInBackground(String... args) {
			try {
				// Retrieve data from ListViewAdapter on click event
				Intent i = getIntent();
				// Get the result of rank
				rank = i.getStringExtra("rank");
				// Get the result of country
				country = i.getStringExtra("country");
				// Get the result of population
				population = i.getStringExtra("population");
				// Get the result of flag
				flag = i.getStringExtra("flag");
				
				// Download the Image from the result URL given by flag
				URL url = new URL(flag);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setDoInput(true);
				conn.connect();
				InputStream is = conn.getInputStream();
				bmImg = BitmapFactory.decodeStream(is);
			} catch (IOException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String args) {
			// Locate the TextViews in singleitemview.xml
			TextView txtrank = (TextView) findViewById(R.id.rank);
			TextView txtcountry = (TextView) findViewById(R.id.country);
			TextView txtpopulation = (TextView) findViewById(R.id.population);
			// Locate the ImageView in singleitemview.xml
			ImageView imgflag = (ImageView) findViewById(R.id.flag);
			
			// Set results to the TextViews
			txtrank.setText(rank);
			txtcountry.setText(country);
			txtpopulation.setText(population);

			// Set results to the ImageView
			imgflag.setImageBitmap(bmImg);

			// Close the progressdialog
			mProgressDialog.dismiss();

		}
	}

}