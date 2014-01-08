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
		
		Intent intent = getIntent();
		selected = intent.getStringExtra("selected");
		
		tapiname = (EditText) findViewById(R.id.txtapiname);
		tpadurl  = (EditText) findViewById(R.id.txtpadurl);
		tport    = (EditText) findViewById(R.id.txtport);
		tapikey  = (EditText) findViewById(R.id.txtapikey);
		
		btnsave = (Button) this.findViewById(R.id.btnsaveapi);
		btnsave.setOnClickListener(this);
		
		btndelete = (Button) this.findViewById(R.id.btndeleteapi);
		btnselect = (Button) this.findViewById(R.id.btnselectapi);

		
		if(!selected.isEmpty()){
			btndelete.setEnabled(true);
			btnselect.setEnabled(true);
			
			PadAPI thisapi = GlobalConfig.apiMap.get(selected);
			
			System.out.println("selected:" + thisapi.getAPIKEY()); //DEBUG
			
			
			tapiname.setText(thisapi.getAPINAME());
			tpadurl.setText(thisapi.getAPIURL());
			tport.setText("" + thisapi.getPORT()); //string conversion needed
			tapikey.setText(thisapi.getAPIKEY());
		}
		
		btndelete.setOnClickListener(this);
		btnselect.setOnClickListener(this);

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
