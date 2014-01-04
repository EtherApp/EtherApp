package de.etherapp.activities;

import de.etherapp.activities.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestTabActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_tab);
		
		Button btnhrhr = (Button) this.findViewById(R.id.btnhrhr);
		btnhrhr.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_tab, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
//		int max = GlobalConfig.getApiCount();
//		System.out.println("apicount: " + max);
//		for (int i = max; i > -1; i--) {
//			GlobalConfig.deleteApi(i);
//		}
		System.out.println("Configs fucked up!");
	}

}
