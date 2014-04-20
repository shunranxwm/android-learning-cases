package com.example.AutoCompleteTextViewDemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

public class AutoCompleteTextViewDemoActivity extends Activity {
	//自动提示字符串数组
	private static final String autoStr[]=new String[] {"Italy","IT","item","its","itself"};
	//AutoCompleteTextView控件
	private AutoCompleteTextView myAutoCompleteTextView=null;
	//MultiAutoCompleteTextView控件
	private MultiAutoCompleteTextView myMultiAutoCompleteTextView=null;
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//新建ArrayAdapter对象,并将autoStr字符串数组传入
		ArrayAdapter arrayAdapter=new ArrayAdapter(
				                                   //@param1 上下文环境
				                                   AutoCompleteTextViewDemoActivity.this,
												   //@param2 样式
				                                   android.R.layout.simple_dropdown_item_1line,
				                                   //@param3 自动提示的字符串数组
				                                   autoStr);
		//获取AutoCompleteTextView控件
		myAutoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.myAutoCompleteTextView);
		//将arrayAdapter对象加入AutoCompleteTextView对象中
		myAutoCompleteTextView.setAdapter(arrayAdapter);


		/**
		 * MultiAutoCompleteTextView继承自AutoCompleteTextView,可以延长自动提示的长度
		 * 它和AutoCompleteTextView的区别就是MultiAutoCompleteTextView可以在输入框中一直增加新的选取值
		 * 编写方式也有所不同，在进行setAdapter之后还需要调用setTokenizer()(分词切分),否则会出现错误
		 */
		myMultiAutoCompleteTextView=(MultiAutoCompleteTextView)findViewById(R.id.myMultiAutoCompleteTextView);
		myMultiAutoCompleteTextView.setAdapter(arrayAdapter);
		//设置分词切分
		myMultiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
	}
}
