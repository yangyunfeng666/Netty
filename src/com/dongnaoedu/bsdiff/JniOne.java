package com.dongnaoedu.bsdiff;

public class JniOne {
	//加载dll文件
	static{
		System.loadLibrary("sss");
	}
	
	private  static  native void getValue();
	
	public static void main(String[] args) {
		
	}
}
