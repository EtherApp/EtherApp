package de.etherapp.activities;

import de.etherapp.activities.R;
import de.etherapp.epclient.PadAPI;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class APISettingsActivity extends Activity implements OnClickListener{
	
	EditText tapiname = null;
	EditText tpadurl = null;
	EditText tport = null;
	EditText tapikey = null;
	Button btnsave = null;
	Button btndelete = null;
	Button btnselect = null;

	String selected = null;
	APISettingsActivity apiact = this;
	
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
			//show delete and select buttons if this is not the selected API
			if(!GlobalConfig.currentApi.getAPIID().equals(selected)){
				btndelete.setVisibility(View.VISIBLE);
				btnselect.setVisibility(View.VISIBLE);
				
				//and give them some click listeners
				btndelete.setOnClickListener(this);
				btnselect.setOnClickListener(this);
			}
			
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
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
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
		        
		        //check whether API works		        
		        if(!pa.checkApi()){
					//On Error show information
		        	runOnUiThread(new Runnable(){
						  public void run()
						  {
							  Toast.makeText(getApplicationContext(), getString(R.string.msgNetAPIErr), Toast.LENGTH_SHORT).show();
						  }
					});
		        }else{
		        	//register globally
		        	GlobalConfig.putNewApi(pa);
		        	
		        	//select this API
		        	GlobalConfig.selectApi(pa.getAPIID());
		        	
		        	//DEBUG
		        	System.out.println("selected " + GlobalConfig.currentApi.getAPINAME() + " on ID: " + GlobalConfig.currentApi.getAPIID());
		        	
			        //refresh and go back to main activity
					Intent intent = new Intent();
					intent.setClassName(getPackageName(),getPackageName()+".MainActivity");
					startActivity(intent);
		        }
			}
			else{ //edit existing API
				String apiname = tapiname.getText().toString();
				String apiurl  = tpadurl.getText().toString();
				int port       = Integer.parseInt(tport.getText().toString());
				String apikey  = tapikey.getText().toString();
				
				//make PadAPI object with new values and existing ID
				PadAPI pa = new PadAPI(apiname, apiurl, port, apikey, selected);
				
				//check whether API works		        
		        if(!pa.checkApi()){
					//On Error show information
		        	runOnUiThread(new Runnable(){
						  public void run(){
							  Toast.makeText(getApplicationContext(), getString(R.string.msgNetAPIErr), Toast.LENGTH_SHORT).show();
						  }
					});
		        }else{
		        	//update in list and DB
					GlobalConfig.updateApi(pa);
		        	
					//select the updated API for use
					GlobalConfig.selectApi(pa.getAPIID());
		        	
		        	
			        //refresh and go back to main activity
					Intent intent = new Intent();
					intent.setClassName(getPackageName(),getPackageName()+".MainActivity");
					startActivity(intent);
		        }
			}
		}
		else if(v == btndelete){
			
    		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

    		//set dialog title
    		alertDialogBuilder.setTitle("Delete API");

    		//set dialog message
    		alertDialogBuilder.setMessage("API \"" + tapiname.getText().toString() + "\" will be deleted. Continue?");
    		alertDialogBuilder.setCancelable(false);

    		alertDialogBuilder.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
 
    				//delete API
    				if(GlobalConfig.getApiCount() > 0){
    					GlobalConfig.deleteApi(selected);
        					
        				runOnUiThread(new Runnable(){
        					public void run(){
        						Toast.makeText(GlobalConfig.ma.getApplicationContext(), "Pad \"" + tapiname.getText().toString() + "\"deleted", Toast.LENGTH_LONG).show();
        					}
        				});
        				apiact.finish();
    				}
    				else{
        				runOnUiThread(new Runnable(){
        					public void run(){
        						Toast.makeText(GlobalConfig.ma.getApplicationContext(), "Could not delete last API. Create a new API, at first.", Toast.LENGTH_LONG).show();
        					}
        				});
    				}
    			}
    		});

    		alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				//if this button is clicked, just close the dialog box and do nothing
    				dialog.cancel();
    			}
    		});

    		//create alert dialog
    		AlertDialog alertDialog = alertDialogBuilder.create();

    		//show it
    		alertDialog.show();  		
		}
		else if(v == btnselect){
			//check whether API works		        
	        PadAPI pa = GlobalConfig.apiMap.get(selected);
			if(!pa.checkApi()){
				//On Error show information
	        	runOnUiThread(new Runnable(){
					  public void run(){
						  Toast.makeText(getApplicationContext(), getString(R.string.msgNetAPIErr), Toast.LENGTH_SHORT).show();
					  }
				});
	        }else{
	        	//actually select it
	        	GlobalConfig.selectApi(selected);
	        	
	        	//refresh and go back to main activity
				Intent intent = new Intent();
				intent.setClassName(getPackageName(),getPackageName()+".MainActivity");
				startActivity(intent);
	        }
		}
	}
}
