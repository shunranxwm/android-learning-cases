package com.example.XMLParserDemo;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Administrator on 14-5-21.
 */
public class XMLHandler extends DefaultHandler {
	//定义一个临时的Tag
	private String mTag=null;

	//XML文件解析的数据
	private XMLParseDataSet mParseDataSet=new XMLParseDataSet();

	//getter方法
	public XMLParseDataSet getmParseDataSet() {
		return mParseDataSet;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		mTag=localName;
		if(mTag.equals("tagwithnumber")) {
			//提取元素属性值
			String _attr=attributes.getValue("thenumber");
			mParseDataSet.setThenumber(_attr);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		//这里必须将标记mTag置为空,否则会对文本节点的读取产生影响
		mTag="";
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);

		String _str=new String(ch, start, length);
		if(mTag.equals("mytag")) {
			mParseDataSet.setMytag(_str);
		}
	}

}
