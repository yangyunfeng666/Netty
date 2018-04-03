package com.netty.socket;

public class LiveMessage {

	public static final int HEAD_TYPE = 1;
	public static final int CONTENT_TYPE = 2;
	private int type;
	private int length ;
	private String content;
	

	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{   type:"+type+"  length:"+length+"  content:"+content+"  }";
	}
}
