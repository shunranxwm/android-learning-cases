package com.example.QRCodeDemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 该程序是实现二维码的功能
 * 在该程序中,首先是检查是否可以联网,如果不可以联网,则就不能生成二维码
 */
public class QRCodeDemoActivity extends Activity {
	private EditText mMyEditText=null;
	private WebView mMyWebView=null;
	private Button mMyButton=null;
	//判断是否可以连接网络的标识
	private boolean mInternetConnection=false;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//找到指定ID的组件
		mMyEditText=(EditText)findViewById(R.id.myEditText);
		mMyWebView=(WebView)findViewById(R.id.myWebView);
		mMyButton=(Button)findViewById(R.id.myButton);

		//按钮事件
		mMyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!mMyEditText.getText().toString().equals("")) {
					new Thread(runnable).start();
				}
			}
		});
	}

	Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			//得到传递过来的数据
			Bundle _bundle=msg.getData();
			mInternetConnection=_bundle.getBoolean("flag");
			//如何可以连接网络
			if(mInternetConnection==true) {
				//根据输入的内容生成二维码
				mMyWebView.loadData(genGoogleQRChart(mMyEditText.getText().toString(), 120),
						            "text/html",
						            "utf-8");
			}
		}
	};


	Runnable runnable=new Runnable() {
		@Override
		public void run() {
			boolean _flag=checkInternetConnection("http://www.baidu.com/", "utf-8");
			//传递消息
			Message _msg=new Message();
			Bundle _bundle=new Bundle();
			_bundle.putBoolean("flag", _flag);
			_msg.setData(_bundle);
			//发送消息
			handler.sendMessage(_msg);
		}
	};

	/**
	 * 检查网络联机是否正常
	 * @param strUrl      链接地址
	 * @param strEncoding 编码方式
	 * @return 返回true则表示可以连接,反之则不能连接
	 */
	private boolean checkInternetConnection(String strUrl, String strEncoding) {
		//设置延迟时间5秒,若超过延迟时间无响应,则无法联机
		int _timeOut=5000;
		try {
			URL _url=new URL(strUrl);
			HttpURLConnection _httpURLConnection=(HttpURLConnection)_url.openConnection();
			_httpURLConnection.setRequestMethod("GET");
			_httpURLConnection.setDoOutput(true);
			_httpURLConnection.setDoInput(true);
			_httpURLConnection.setRequestProperty("User-Agent","Mozilla/4.0"+" (compatible; MSIE 6.0; Windows 2000)");
			_httpURLConnection.setRequestProperty("Content-type","text/html; charset="+strEncoding);
			_httpURLConnection.setConnectTimeout(_timeOut);
			_httpURLConnection.connect();
			if(_httpURLConnection.getResponseCode()==200) {
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将需要生成二维码的字符串组成Google API所需参数的字符串
	 * @param strToQRCode 需要生成二维码的字符串
	 * @param strWidth 二维码的宽度
	 * @return 返回Google API所需的参数字符串
	 */
	private String genGoogleQRChart(String strToQRCode, int strWidth) {
		String _strReturn="";
		try {
			_strReturn=new String(strToQRCode.getBytes("utf-8"));
			//组成Google  API所需要的传输的参数
			_strReturn = "<html><body>"+
					     "<img src=http://chart.apis.google.com/chart?"+
					     "chs="+strWidth+"x"+strWidth+
					     "&chl="+URLEncoder.encode(_strReturn, "utf-8")+
					     "&choe=UTF-8&cht=qr>"+
					     "</body></html>";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return _strReturn;
	}

}
