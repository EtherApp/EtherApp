package de.etherapp.activities;

import de.etherapp.app.R;
import de.etherapp.epclient.PadAPI;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class APISettingsActivity extends Activity implements OnClickListener{
	
	EditText tapiname = null;
	EditText tpadurl = null;
	EditText tport = null;
	EditText tapikey = null;
	Button btnsave = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		tapiname = (EditText) findViewById(R.id.txtapiname);
		tpadurl = (EditText) findViewById(R.id.txtpadurl);
		tport = (EditText) findViewById(R.id.txtport);
		tapikey = (EditText) findViewById(R.id.txtapikey);
		
		btnsave = (Button) this.findViewById(R.id.btnsave);
		btnsave.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		String apiname = tapiname.getText().toString();
		String padurl = tpadurl.getText().toString();
		String port =  tport.getText().toString();
		String apikey = tapikey.getText().toString();

		System.out.println(apiname + "  " + padurl + "  " + port + "   " + apikey);
		
		//INIT API
		PadAPI pa = new PadAPI(apiname, padurl + ":" + port, apikey);
        int pos = GlobalConfig.putNewApi(pa);
        System.out.println(pos);
        GlobalConfig.selectApi(pos);
        System.out.println("saved " + GlobalConfig.currentApi.getAPINAME() + "on pos: " + pos);
        this.finish();
	}

}
