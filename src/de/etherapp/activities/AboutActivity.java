package de.etherapp.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		TextView tv = (TextView) findViewById(R.id.txtAbout);
		tv.setText("EtherApp - an admin app for Etherpad Lite\n\n" +
				"2014 by\n" +
				"Martin Stoffers\n" +
				"Ferdinand Malcher\n" +
				"HfT Leipzig\n\n\n" +
				"This software is under CC-BY 4.0 licence.\n" +
				"You are free to share and adapt this work, even for commercial purposes. But you must give appropriate credit, provide a link to the license, and indicate if changes were made."
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    		case R.id.action_settings:
	    			Intent i = new Intent(this, SettingsListActivity.class);
	            	startActivity(i);
	            	return true;
	    		case R.id.action_quit:
	    			this.finish();
	            	return true;
	            default:
	                return super.onOptionsItemSelected(item);
	    }
	}

}
