package de.etherapp.activities;

import de.etherapp.activities.R;
import de.etherapp.epclient.PadAPI;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class APISettingsActivity extends Activity implements OnClickListener{
	
	EditText tapiname = null;
	EditText tpadurl = null;
	EditText tport = null;
	EditText tapikey = null;
	Button btnsave = null;
	Button btndelete = null;
	Button btnselect = null;

	String selected = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		//the API ID given from the calling activity
		//is empty if new API shall be created
		Intent intent = getIntent();
		selected = intent.getStringExtra("selected");
		
		//the text fields
		tapiname = (EditText) findViewById(R.id.txtapiname);
		tpadurl  = (EditText) findViewById(R.id.txtpadurl);
		tport    = (EditText) findViewById(R.id.txtport);
		tapikey  = (EditText) findViewById(R.id.txtapikey);
		
		//the buttons
		btnsave = (Button) this.findViewById(R.id.btnsaveapi);
		btndelete = (Button) this.findViewById(R.id.btndeleteapi);
		btnselect = (Button) this.findViewById(R.id.btnselectapi);

		//save button is always needed, so give him a click listener
		btnsave.setOnClickListener(this);
		
		
		if(!selected.isEmpty()){ //if a API ID is given (user wants to update API)
			//buttons are hidden - show them			
			btndelete.setVisibility(View.VISIBLE);
			btnselect.setVisibility(View.VISIBLE);
			
			//and give them some click listeners
			btndelete.setOnClickListener(this);
			btnselect.setOnClickListener(this);
			
			//get the desired API
			PadAPI thisapi = GlobalConfig.apiMap.get(selected);
			
			//fill text fields with values from API
			tapiname.setText(thisapi.getAPINAME());
			tpadurl.setText(thisapi.getAPIURL());
			tport.setText("" + thisapi.getPORT()); //string conversion needed
			tapikey.setText(thisapi.getAPIKEY());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		if(v == btnsave){
			if(selected.isEmpty()){ //new API shall be created
				String apiname = tapiname.getText().toString();
				String apiurl  = tpadurl.getText().toString();
				int port       = Integer.parseInt(tport.getText().toString());
				String apikey  = tapikey.getText().toString();

				//DEBUG
				System.out.println(apiname + "  " + apiurl + "  " + port + "   " + apikey);
				
				//build PadAPI object from data
				PadAPI pa = new PadAPI(apiname, apiurl, port, apikey);
		        GlobalConfig.putNewApi(pa);
		        
		        //TODO: Check whether API works
		        		        
		        GlobalConfig.selectApi(pa.getAPIID());
		        System.out.println("saved " + GlobalConfig.currentApi.getAPINAME() + " on ID: " + GlobalConfig.currentApi.getAPIID());
		        this.finish();
			}
			else{ //edit existing API
				String apiname = tapiname.getText().toString();
				String apiurl  = tpadurl.getText().toString();
				int port       = Integer.parseInt(tport.getText().toString());
				String apikey  = tapikey.getText().toString();

				System.out.println(apiname + "  " + apiurl + "  " + port + "   " + apikey);
				
				//make PadAPI object with new values and existing ID
				PadAPI pa = new PadAPI(apiname, apiurl, port, apikey, selected);
				
				//update in list and DB
				GlobalConfig.updateApi(pa);
				
				//select the updated API for use
				GlobalConfig.selectApi(pa.getAPIID());
				
		        this.finish();
			}
		}
		else if(v == btndelete){
			if(GlobalConfig.getApiCount() > 0){
				GlobalConfig.deleteApi(selected);
			}
			this.finish();
		}
		else if(v == btnselect){
			GlobalConfig.selectApi(selected);
			this.finish();
		}

	}

}
