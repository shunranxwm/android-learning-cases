package com.example.XMLParserDemo;

import java.io.Serializable;

/**
 * Created by Administrator on 14-5-21.
 */
public class XMLParseDataSet implements Serializable {

	private String mytag=null;
	private String thenumber=null;

	public void setMytag(String mytag) {
		this.mytag = mytag;
	}

	public void setThenumber(String thenumber) {
		this.thenumber = thenumber;
	}

	@Override
	public String toString() {
		return "XMLParseDataSet{" +
				"mytag='" + mytag + '\'' +
				", thenumber='" + thenumber + '\'' +
				'}';
	}
}
