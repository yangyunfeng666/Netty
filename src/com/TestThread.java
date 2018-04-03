package com;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestThread {

	public static void main(String[] args) {

		TestClass testclass = new TestClass();
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					testclass.Method(Thread.currentThread());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}, "t1");

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					testclass.Method(Thread.currentThread());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, "t2");
	
		t1.start();
		t2.start();
	
		

	}

	private static class TestClass {

//		ReentrantLock lock = new ReentrantLock();
		
		ReadWriteLock lock = new ReentrantReadWriteLock();

		public void Method(Thread thread) throws InterruptedException {
			// 直接取得锁 没有取得锁的等待
			// lock.lock();
			// try {
			// System.out.println("hello 我的名字是" + thread.getName() + "现在取得锁");
			// } catch (Exception e) {
			//
			// } finally {
			// System.out.println("线程名"+thread.getName() + "释放了锁");
			// lock.unlock();
			// }
			
		

			// if(lock.tryLock()){//尝试取得锁，如果失败 这干其他的
			// try {
			// System.out.println("hello 我的名字是" + thread.getName() + "现在取得锁");
			// } catch (Exception e) {
			// System.out.println("hello 我的名字是" + thread.getName() + "加锁出现问题了");
			// } finally {
			// System.out.println("线程名"+thread.getName() + "释放了锁");
			// lock.unlock();
			// }
			// }else{
			// System.out.println("线程名"+thread.getName() + "加锁失败有人占用了锁");
			// }

			// 如果在加锁等待时候可以被中段，然后干其他的事情
//			 try {
//			 lock.lockInterruptibly();
//			 if(thread.getName().equals("t1")){
//			 System.out.println("hello 我的名字是" + thread.getName() +
//			 "现在取得锁有"+lock.getHoldCount()+"个");
//			 //第二线程在加锁时候中断去干其他事情
//			 }
//			 System.out.println("hello 我的名字是" + thread.getName() + "现在取得锁");
//			 } catch (Exception e) {
//			 System.out.println("hello 我的名字是" + thread.getName() + "加锁出现问题了");
//			 } finally {
//			 System.out.println("线程名" + thread.getName() + "释放了锁");
//			 lock.unlock();
//			 }
			//
//	
			//写的锁
			 try {
				 lock.writeLock().lock();
				 if(thread.getName().equals("t1")){
				 System.out.println("hello 我的名字是" + thread.getName() +
				 "现在取得写锁");
				 //第二线程在加锁时候中断去干其他事情
				 }
				 System.out.println("hello 我的名字是" + thread.getName() + "现在取得写锁");
				 } catch (Exception e) {
				 System.out.println("hello 我的名字是" + thread.getName() + "加锁出现问题了");
				 } finally {
				 System.out.println("线程名" + thread.getName() + "释放了写锁");
				 lock.writeLock().unlock();
				 }
			
			//读的锁
			 try {
				 lock.readLock().lock();
				 if(thread.getName().equals("t1")){
				 System.out.println("hello 我的名字是" + thread.getName() +
				 "现在取得读锁");
				 //第二线程在加锁时候中断去干其他事情
				 }
				 System.out.println("hello 我的名字是" + thread.getName() + "现在取得读锁");
				 } catch (Exception e) {
				 System.out.println("hello 我的名字是" + thread.getName() + "加锁出现问题了");
				 } finally {
				 System.out.println("线程名" + thread.getName() + "释放了读锁");
				 lock.readLock().unlock();
				 }
			 
			
			
		}

	}
}
