package com.example.ActivitySwitchToActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActivitySwitchToActivity_MainActivity extends Activity {

	private Button mButton=null;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mButton=(Button)findViewById(R.id.myButton1);
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent _intent=new Intent(ActivitySwitchToActivity_MainActivity.this, SecondActivity.class);
				_intent.putExtra("data", "hello");
				startActivityForResult(_intent, 123);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode==456) {
			String _strValue=data.getExtras().getString("data");
			Toast.makeText(this, _strValue, Toast.LENGTH_LONG).show();
		}
	}
}
