package com.example.BlackListDemo;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BlackListDemoActivity extends Activity {
	private TextView mMyTextView01=null;
	private TextView mMyTextView03=null;
	private EditText mMyEditText=null;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//找到指定ID的组件
		mMyTextView01=(TextView)findViewById(R.id.myTextView01);
		mMyTextView03=(TextView)findViewById(R.id.myTextView03);
		mMyEditText=(EditText)findViewById(R.id.myEditText);

		//设定TelephonyManager去抓取Telephony Service
		TelephonyManager _telManager=(TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
		//设定PhoneCallListener
		PhoneCallListener _phoneCallListener=new PhoneCallListener();
		//监听
		_telManager.listen(_phoneCallListener, PhoneCallListener.LISTEN_CALL_STATE);
	}

	public class PhoneCallListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			//判断手机目前的状态
			switch (state) {
				//待机状态
				case TelephonyManager.CALL_STATE_IDLE:
					mMyTextView01.setText(R.string.str_CALL_STATE_IDLE);
					try {
						AudioManager _audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
						if(_audioManager!=null) {
							//手机待机时,响铃模式为正常
							_audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
							_audioManager.getStreamVolume(AudioManager.STREAM_RING);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				//通话状态
				case TelephonyManager.CALL_STATE_OFFHOOK:
					mMyTextView01.setText(R.string.str_CALL_STATE_OFFHOOK);
					break;

				//来电状态
				case TelephonyManager.CALL_STATE_RINGING:
					mMyTextView01.setText(R.string.str_CALL_STATE_RINGING + incomingNumber);
					//判断来电与输入的电话是否一致,当一样时响铃模式为静音
					if(incomingNumber.equals(mMyTextView03.getText().toString())) {
						try {
							AudioManager _audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
							if(_audioManager!=null) {
								//黑名单来电时,设为静音模式
								_audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
								_audioManager.getStreamVolume(AudioManager.STREAM_RING);
								Toast.makeText(BlackListDemoActivity.this, R.string.str_msg, Toast.LENGTH_LONG).show();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
			}
			super.onCallStateChanged(state, incomingNumber);

			mMyEditText.setOnKeyListener(new View.OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					//将EditText里输入的数据同步显示到TextView
					mMyTextView03.setText(mMyEditText.getText());
					return false;
				}
			});
		}

	}
}
