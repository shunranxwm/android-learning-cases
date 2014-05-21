package com.example.XMLParserDemo;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.Serializable;
import java.net.URL;

public class XMLParserDemoActivity extends Activity {
	private TextView mTextView=null;

	//XML服务器地址
	private String mXMLURL="http://192.168.1.127:8080/XML/xmltest.xml";

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mTextView=(TextView)findViewById(R.id.myTextView);

		new Thread(runnable).start();
	}

	Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			//接收传递过来的数据
			Bundle _bundle=msg.getData();
			XMLParseDataSet _xmlParseDataSet=(XMLParseDataSet)_bundle.getSerializable("data");

			//显示结果
			mTextView.setText(_xmlParseDataSet.toString());
		}
	};

	Runnable runnable=new Runnable() {
		@Override
		public void run() {
			//解析XML,并返回解析结果
			XMLParseDataSet _xmlParseDataSet=parseXML(mXMLURL);


			//将结果返回消息处理
			Message _msg=new Message();
			Bundle _bundle=new Bundle();
			_bundle.putSerializable("data", _xmlParseDataSet);
			_msg.setData(_bundle);
			handler.sendMessage(_msg);
		}
	};

	/**
	 * 从服务器读取XML文件并解析XML,返回XML解析的结果
	 * @param xmlURL XML文件在服务器上的地址
	 * @return 返回解析的结果
	 */
	private XMLParseDataSet parseXML(String xmlURL) {
		//记录XML解析的结果
		XMLParseDataSet _xmlParseDataSet=new XMLParseDataSet();

		//读取并解析XML文件
		try {
			//创建XML文件的URL
			URL _url=new URL(xmlURL);
			//从SAXParserFactory获取SAXParser
			SAXParserFactory _spf=SAXParserFactory.newInstance();
			SAXParser _sp=_spf.newSAXParser();
			//从SAXParser获取XMLReader
			XMLReader _xr=_sp.getXMLReader();

			//为XMLReader创建我们自己的XML内容处理器
			XMLHandler _xmlHandler=new XMLHandler();
			_xr.setContentHandler(_xmlHandler);

			//XMLReader解析XML文件
			_xr.parse(new InputSource(_url.openStream()));

			//获得解析的结果
			_xmlParseDataSet=_xmlHandler.getmParseDataSet();

			System.out.println("------" + _xmlParseDataSet.toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return _xmlParseDataSet;
	}

}
