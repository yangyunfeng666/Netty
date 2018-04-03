package com.netty.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.util.logging.Logger;

public class LongConnectServer {

	private String address = "localhost";
	private int port = 5230;

	public void testConnecte() throws IOException {
		System.out.println("连接服务");
		Socket socket = new Socket(address, port);
		// socket.connect(socketAddress);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String readline;
		readline = in.readLine();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(socket!=null){
						try {
							String result = br.readLine();
							if(result!=null){
							System.out.println("result:"+result);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		}).start();
		// in.readLine();
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// while(true){
		// // TODO Auto-generated method stub
		// byte[] bytes = new byte[64];
		// try {
		// int lenth = socket.getInputStream().read(bytes);
		// System.out.println("等到数据项" + lenth);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }
		// }).start();
		while (!readline.equals("end")) {
			int code;
			code = Integer.parseInt(readline);
			System.out.println("输入的code是" + code);
			if (code == 0) {// 关闭
				break;

			} else if (code == LiveMessage.HEAD_TYPE) {// 写心跳数据 协议定义是type 1个字节
														// 数据长度length 4个字节
														// content 最后是数据
				ByteBuffer buffer = ByteBuffer.allocate(5);
				buffer.put((byte) 1);
				buffer.putInt(0);
				socket.getOutputStream().write(buffer.array());
				System.out.println("发送想跳包");
			} else if (code == LiveMessage.CONTENT_TYPE) {
				byte[] content = ("hello world" + hashCode()).getBytes();
				ByteBuffer buffer = ByteBuffer.allocate(content.length + 5);
				buffer.put((byte) 2);
				buffer.putInt(content.length);
				buffer.put(content);
				socket.getOutputStream().write(buffer.array());
				System.out.println("写传输数据");
			}
			readline = in.readLine();
		}
		socket.close();
	}

	public static void main(String[] args) {
		try {

			new LongConnectServer().testConnecte();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
