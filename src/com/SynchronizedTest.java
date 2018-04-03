package com;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SynchronizedTest {
	public static void main(String[] args) {
		TestClass A = new TestClass();
		 TestClass B = new TestClass();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					A.Method1(Thread.currentThread());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "t1");
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					B.Method1(Thread.currentThread());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, "t2");
		t1.start();
		t2.start();
	}
}
