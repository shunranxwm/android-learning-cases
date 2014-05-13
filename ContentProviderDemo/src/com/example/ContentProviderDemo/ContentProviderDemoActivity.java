package com.example.ContentProviderDemo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class ContentProviderDemoActivity extends Activity {
	private TextView mtextView=null;
	private EditText meditText=null;
	private EditText meditText2=null;
	private Button mButton=null;

	private static final int PICK_CONTACT_SUBACTIVITY=2;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//获取指定ID的组件
		mtextView=(TextView)findViewById(R.id.myTextView);
		meditText=(EditText)findViewById(R.id.myEditText);
		meditText2=(EditText)findViewById(R.id.myEditText2);
		mButton=(Button)findViewById(R.id.myButton);

		//按钮的事件监听器
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//构建Uri来取得联系人的资源位置
				//Uri _uri=Uri.parse("content://contacts/people");
				//通过Intent来取得联系人数据并传回所选中的值
				//Intent _intent=new Intent(Intent.ACTION_PICK, _uri);
				//开启新的Activity并希望该Activity能回传值
				//startActivityForResult(_intent, PICK_CONTACT_SUBACTIVITY);

				//上面的操作可以直接写成如下
				startActivityForResult(
						new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI),
						PICK_CONTACT_SUBACTIVITY);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			switch (requestCode) {
				case PICK_CONTACT_SUBACTIVITY:
					final Uri _uriRet=data.getData();
					if(_uriRet!=null) {
						try {
							//查询
							Cursor _cursor=this.managedQuery(_uriRet, null, null, null, null);
							//将Cursor移到最前端
							_cursor.moveToFirst();
							//取得联系人的姓名
							String _name=_cursor.getString(_cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
							//将姓名写入EditText
							meditText.setText(_name);
							//取得联系人的电话
							int _contactId=_cursor.getInt(_cursor.getColumnIndex(ContactsContract.Contacts._ID));
							Cursor phones=getContentResolver().query(
								ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
								null,
								ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = "+_contactId,
								null,
								null
							);
							StringBuffer _sb=new StringBuffer();
							int typePhone, resType;
							String numPhone;
							if (phones.getCount() > 0) {
								phones.moveToFirst();
	                			/* 2.0可以允许User设定多组电话号码，但本范例只捞一组电话号码作示范 */
								typePhone = phones.getInt ( phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE) );
								numPhone = phones.getString ( phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER) );
								resType = ContactsContract.CommonDataKinds.Phone.getTypeLabelResource(typePhone);
								//显示的样式为:Mobile:1234567890
								_sb.append(getString(resType) +": "+ numPhone +"\n");
	               				 /*将电话写入EditText02中*/
								meditText2.setText(numPhone);
							}else{
								_sb.append("no Phone number found");
							}
	             			 /*Toast是否读取到完整的电话种类与电话号码*/
							Toast.makeText(this, _sb.toString(), Toast.LENGTH_SHORT).show();
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				default:
					break;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
