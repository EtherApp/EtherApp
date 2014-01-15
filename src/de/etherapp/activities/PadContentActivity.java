package de.etherapp.activities;

import java.util.HashMap;

import org.etherpad_lite_client.EPLiteException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

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
				
		//content text field
		padcontent = (TextView) findViewById(R.id.txtPadContent);
		
		
//		HashMap result = null;
//		try{
//			result = GlobalConfig.currentApi.getClient().getText(padid);
//		}
//		catch(EPLiteException e){
//			System.out.println(e);
//		}
//		System.out.println(result);
//		
		//String text = (String) result.get("text");
		padcontent.setText(padid);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pad_content, menu);
		return true;
	}

}
