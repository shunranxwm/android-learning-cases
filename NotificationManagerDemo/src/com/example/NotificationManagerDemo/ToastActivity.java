package com.example.NotificationManagerDemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Administrator on 14-4-20.
 */
public class ToastActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*发出Toast*/
		Toast.makeText(ToastActivity.this, "这是模拟MSN切换登陆状态的程序", Toast.LENGTH_SHORT).show();
		finish();
	}
}
