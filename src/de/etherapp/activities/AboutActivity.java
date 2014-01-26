package de.etherapp.activities;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		//get textview
		TextView tv = (TextView) findViewById(R.id.txtAbout);
		
		//make textview scrollable
		tv.setMovementMethod(new ScrollingMovementMethod());
		
		//set the text
		tv.setText("EtherApp - An admin app for Etherpad Lite\n"
				+ "http://www.etherapp.de\n\n"
				+ "2014 by\n"
				+ "Martin Stoffers\n"
				+ "Ferdinand Malcher\n"
				+ "HfT Leipzig\n\n\n"
				+ "This program is free software: you can redistribute it "
				+ "and/or modify it under the terms of the GNU General "
				+ "Public License as published by the Free Software Foundation, "
				+ "either version 3 of the License, or (at your option) any "
				+ "later version.\n\n"
				+ " This program is distributed in the hope that it will be useful,"
				+ "but WITHOUT ANY WARRANTY; without even the implied warranty of"
				+ " MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the"
				+ "GNU General Public License for more details.\n\n"
				+ "You should have received a copy of the GNU General Public License "
				+ "along with this program.\n"
				+ "If not, see <http://www.gnu.org/licenses/>.\n\n\n"
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
