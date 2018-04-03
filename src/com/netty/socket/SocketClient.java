package com.netty.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {

	
public static void main(String[] args) {
	
	try {
		Socket socket = new Socket("localhost", 5230);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(socket.getOutputStream());
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String readline;
		readline = br.readLine();
		while(!readline.equals("end")){
			pw.println(readline);
			pw.flush();
			// 刷新输出流，使Server马上收到该字符串
		              System.out.println("Client:" + readline);
		             // 在系统标准输出上打印读入的字符串
		                System.out.println("Server:" + in.readLine());
			readline = br.readLine();
		}
		pw.close();
		in.close();
		socket.close();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
}
