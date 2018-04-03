package com;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestClass {
		public static synchronized  void Method1(Thread a) {
			try {
//				synchronized(String.class)
				 {
					System.out.println(
							a.getName() + "start 锁" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					Thread.sleep(2000);
					System.out.println(
							a.getName() + "end 锁" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		public static synchronized void Method2(Thread a) {
			try {
//				synchronized (Integer.class) 
				{
					System.out.println(
							a.getName() + "start 锁" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					System.out.println(
							a.getName() + "end 锁" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				}
				} catch (Exception e) {
			} finally {
			}
		}
}