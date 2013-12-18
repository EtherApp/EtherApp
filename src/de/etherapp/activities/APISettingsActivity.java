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
		tpadurl = (EditText) findViewById(R.id.txtpadurl);
		tport = (EditText) findViewById(R.id.txtport);
		tapikey = (EditText) findViewById(R.id.txtapikey);
		
		btnsave = (Button) this.findViewById(R.id.btnsaveapi);
		btnsave.setOnClickListener(this);
		
		btndelete = (Button) this.findViewById(R.id.btndeleteapi);
		btnselect = (Button) this.findViewById(R.id.btnselectapi);

		
		if(!selected.equals("-1")){
			btndelete.setEnabled(true);
			btnselect.setEnabled(true);
			
			tapiname.setText(GlobalConfig.apiList.get(Integer.parseInt(selected)).getAPINAME());
			tpadurl.setText(GlobalConfig.apiList.get(Integer.parseInt(selected)).getAPIURL());
			tport.setText("9001");
			tapikey.setText(GlobalConfig.apiList.get(Integer.parseInt(selected)).getAPIKEY());
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
			if(selected.equals("-1")){
				String apiname = tapiname.getText().toString();
				String apiurl = tpadurl.getText().toString();
				String port =  tport.getText().toString();
				String apikey = tapikey.getText().toString();

				System.out.println(apiname + "  " + apiurl + "  " + port + "   " + apikey);
				
				//INIT API
				PadAPI pa = new PadAPI(apiname, apiurl + ":" + port, apikey);
		        int pos = GlobalConfig.putNewApi(pa);
		        System.out.println(pos);
		        GlobalConfig.selectApi(pos);
		        System.out.println("saved " + GlobalConfig.currentApi.getAPINAME() + " on pos: " + pos);
		        this.finish();	
			}
			else{
				String apiname = tapiname.getText().toString();
				String apiurl = tpadurl.getText().toString();
				String port =  tport.getText().toString();
				String apikey = tapikey.getText().toString();

				System.out.println(apiname + "  " + apiurl + "  " + port + "   " + apikey);
				
				//INIT API
		        GlobalConfig.updateApi(Integer.parseInt(selected), apiname, apiurl, apikey);
		        GlobalConfig.selectApi(Integer.parseInt(selected));
		        System.out.println("updated " + GlobalConfig.currentApi.getAPINAME());
		        this.finish();
			}
		}
		else if(v == btndelete){
			if(GlobalConfig.getApiCount() > 0){
				GlobalConfig.deleteApi(Integer.parseInt(selected));
			}
			this.finish();
		}
		else if(v == btnselect){
			GlobalConfig.selectApi(Integer.parseInt(selected));
			this.finish();
		}

	}

}
