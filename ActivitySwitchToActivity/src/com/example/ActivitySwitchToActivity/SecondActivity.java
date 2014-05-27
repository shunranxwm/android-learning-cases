package com.example.ActivitySwitchToActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Administrator on 14-5-26.
 */
public class SecondActivity extends Activity {
	private Button mButton=null;
	private Intent mIntent=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_second);

		mIntent=this.getIntent();
		String _strValue;
		if(mIntent!=null) {
			_strValue=mIntent.getExtras().getString("data");
			Toast.makeText(this, _strValue, Toast.LENGTH_LONG).show();
		}

		mButton=(Button)findViewById(R.id.myButton2);
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mIntent.putExtra("data", "oh, yes");
				setResult(456, mIntent);
				finish();
			}
		});
	}
}
