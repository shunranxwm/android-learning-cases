package com.example.NotificationManagerDemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NotificationManagerDemoActivity extends Activity {
	/*声明对象变量*/
	private Spinner myLoginStatusSpinner=null;
	private NotificationManager myNotificationManager=null;
	private ArrayAdapter<String> myArrayAdapter=null;
	private Resources resources=null;

	/*定义字符串数组*/
	private static final String[] loginStatus=new String[]{"在线","离开","忙碌中","马上回来","脱机"};

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/*初始化对象*/
		myNotificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		myLoginStatusSpinner= (Spinner) findViewById(R.id.myLoginStatusSpinner);
		myArrayAdapter= new ArrayAdapter<String>(NotificationManagerDemoActivity.this,
				                                 android.R.layout.simple_dropdown_item_1line,
				                                 loginStatus);
		/*采用自定义的myspinner_dropdown下拉菜单样式*/
		myArrayAdapter.setDropDownViewResource(R.layout.myspinner_dropdown);
		/*将myArrayAdapter对象加入myLoginStatusSpinner对象中*/
		myLoginStatusSpinner.setAdapter(myArrayAdapter);
		/*为myLoginStatusSpinner设置监听器*/
		myLoginStatusSpinner.setOnItemSelectedListener(new MySpinnerListener());
	}

	/*当Spinner中的某个项目被点击时,则触发该监听器*/
	class MySpinnerListener implements Spinner.OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			/*定义一个字符串临时变量*/
			String _tempStr=loginStatus[position];
			/*根据选择的item来判断要发出哪一个Notification*/
			if(_tempStr.equals("在线")) {
				setNotificationType(R.drawable.msn, _tempStr);
			}
			else if(_tempStr.equals("离开")) {
				setNotificationType(R.drawable.away, _tempStr);
			}
			else if(_tempStr.equals("忙碌中")) {
				setNotificationType(R.drawable.busy, _tempStr);
			}
			else if(_tempStr.equals("马上回来")) {
				setNotificationType(R.drawable.min, _tempStr);
			}
			else if(_tempStr.equals("脱机")) {
				setNotificationType(R.drawable.offine, _tempStr);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	}

	/**
	 * 当Spinner中的某项被点击时,发出Notification通知的方法
	 * @param iconId 图片的ID
	 * @param text 通知显示的文本
	 */
	private void setNotificationType(int iconId, String text) {
		/*建立新的Intent,执行Activity*/
		Intent notifyIntent=new Intent(this, ToastActivity.class);
		notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		/*建立PendingIntent,延时执行Activity*/
		PendingIntent pendingIntent=PendingIntent.getActivity(this,
				                                              0,
				                                              notifyIntent,
				                                              0);

		/**
		 * 下面是Android3.0之后,使用Notification.Builder代替了Notification
		 */
		//建立Notification.Builder
		Notification.Builder builder=new Notification.Builder(this);
		resources=this.getResources();
		//设置builder的参数
		builder.setContentIntent(pendingIntent)
			   .setSmallIcon(iconId)
			   .setLargeIcon(BitmapFactory.decodeResource(resources, iconId))
			   .setTicker(text)
			   .setContentText("aaa")
			   .setContentTitle("bbb")
			   .setWhen(System.currentTimeMillis())
			   .setAutoCancel(true);
		//获取Notification
		Notification myNotification=builder.build();
		//送出Notification
		myNotificationManager.notify(0, myNotification);


		/**
		 * 下面是Android3.0之前通知的用法
		 */
		/*//建立Notification,并设定相关参数
		Notification myNotification2=new Notification();
		//设定显示的图标
		myNotification2.icon=iconId;
		//设定显示的文字信息
		myNotification2.tickerText=text;
		//设定发出的声音
		myNotification2.defaults=Notification.DEFAULT_SOUND;
		//设定Notification留言条的参数
		myNotification2.setLatestEventInfo(NotificationManagerDemoActivity.this,
				                          "MSN登陆状态",
				                          text,
				                          pendingIntent);
		//送出Notification
		myNotificationManager.notify(0, myNotification2);*/
	}
}
