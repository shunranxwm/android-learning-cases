package com.example.CallDemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallDemoActivity extends Activity {
	private EditText meditText=null;
	private Button mbutton=null;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//获取指定ID的组件
		meditText=(EditText)findViewById(R.id.myEditText);
		mbutton=(Button)findViewById(R.id.myButton);

		//按钮监听器
		mbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					//取得用户输入的字符串
					String _editText=meditText.getText().toString();

					//第一种写法
					//Intent _intent=new Intent("android.intent.action.CALL", Uri.parse("tel:"+_editText));

					//第二种写法
					Intent _intent=new Intent();
					//ACTION_DIAL:显示拨号界面,但不拨打电话
					//ACTION_CALL:直接拨打电话
					_intent.setAction(Intent.ACTION_DIAL);
					_intent.setData(Uri.parse("tel:"+_editText));

					startActivity(_intent);
				    meditText.setText("");
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * 检查字符串是否为合法的电话号码方法
	 * @param phoneNumber 电话号码字符串
	 * @return 返回true则表示电话号码字符串合法,反之则非法
	 */
	/*private static boolean isPhoneNumberValid(String phoneNumber) {
		boolean _isValid=false;

		//电话号码的格式可以有:
		//(123)456-7890
		//123-456-7890
		//1234567890
		//(123)-456-7890
		String _expression1="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
		String _expression2="^\\(?(\\d{2})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";

		Pattern _pattern=Pattern.compile(_expression1);
		Matcher _matcher=_pattern.matcher(phoneNumber);

		Pattern _pattern2=Pattern.compile(_expression2);
		Matcher _matcher2=_pattern2.matcher(phoneNumber);

		if(_matcher.matches() || _matcher2.matches()) {
			_isValid=true;
		}
		return false;
	}*/

}
