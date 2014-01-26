package de.etherapp.activities;

import java.util.HashMap;

import org.etherpad_lite_client.EPLiteException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class PadContentActivity extends Activity {
	
	String padid = null;
	TextView padcontent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_padcontent);
		
		//the API ID given from the calling activity
		//is empty if new API shall be created
		Intent intent = getIntent();
		padid = intent.getStringExtra("padid");
		
		// Set Title to padid
		setTitle(padid);
		
		//content text field
		padcontent = (TextView) findViewById(R.id.txtPadContent);
		
		//make textview scrollable
		padcontent.setMovementMethod(new ScrollingMovementMethod());
		
		//get text from API
		HashMap result = null;
		try{
			result = GlobalConfig.currentApi.getClient().getText(padid);
			String text = (String) result.get("text");

			//apply to textview
			padcontent.setText(text);
		}
		catch(EPLiteException e){
			//On Error show information
			runOnUiThread(new Runnable(){
				  public void run(){
					  Toast.makeText(getApplicationContext(), "Could not retrieve Text from pad.", Toast.LENGTH_LONG).show();
				  }
			});
			finish();
		}	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pad_content, menu);
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
	    		case R.id.action_about:
	    			Intent j = new Intent(this, AboutActivity.class);
	            	startActivity(j);
	            	return true;
	    		case R.id.action_quit:
	    			this.finish();
	            	return true;
	            default:
	                return super.onOptionsItemSelected(item);
	    }
	}
}
