package com.example.GoogleTranslateDemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.*;

public class GoogleTranslateDemoActivity extends Activity {
	private Spinner mSelect=null;
	private EditText mInput=null;
	private Button mSubmit=null;
	private WebView mOutput=null;
	private TextView mTips=null;
	private String[] mSpinnerStr={"简体中文->英文", "英文->简体中文"};
	private String[] mUrl={"file:///android_asset/cn2en.html", "file:///android_asset/en2cn.html"};

	//利用Handler实现线程通信
	private Handler mHandler=new Handler();

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//获得指定ID的组件
		mSelect=(Spinner)findViewById(R.id.select);
		mInput=(EditText)findViewById(R.id.input);
		mSubmit=(Button)findViewById(R.id.submit);
		mOutput=(WebView)findViewById(R.id.output);
		mTips=(TextView)findViewById(R.id.tips);

		//配置一个适配器
		ArrayAdapter<String> _adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mSpinnerStr);
		_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSelect.setPrompt("请选择翻译方式：");

		//Spinner事件监听器
		mSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				//根据选择来设置翻译模式
				//选择的是"简体中文->英文"
				if(mSpinnerStr[position].equals(mSpinnerStr[0])) {
					mOutput.loadUrl(mUrl[0]);
				}
				//选择的是"英文->简体中文"
				if(mSpinnerStr[position].equals(mSpinnerStr[1])) {
					mOutput.loadUrl(mUrl[1]);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				//默认选择的是"简体中文->英文"
				mOutput.loadUrl(mUrl[0]);
			}
		});

		//添加适配器
		mSelect.setAdapter(_adapter);

		//获得WebView的settings
		WebSettings _webSettings=mOutput.getSettings();
		_webSettings.setJavaScriptEnabled(true);
		_webSettings.setSaveFormData(false);
		_webSettings.setSavePassword(false);
		_webSettings.setSupportZoom(false);

		//提交按钮的监听器
		mSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//提示可见
				mTips.setVisibility(View.VISIBLE);
				//翻译结果可见
				mOutput.setVisibility(View.VISIBLE);
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if(!mInput.getText().toString().equals("")) {
							System.out.println("------" + mInput.getText().toString());
							mOutput.loadUrl("javascript:translate('" + mInput.getText().toString() + "')");
						}
					}
				});
			}
		});
	}
}
