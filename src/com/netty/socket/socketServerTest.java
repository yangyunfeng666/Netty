package com.netty.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class socketServerTest {

	public static void main(String[] args) {
		new socketServerTest().onServer();
	}
	
	public void onServer(){
		ServerSocket server = null;
		try {
			server = new ServerSocket(5230);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("服务器启动成功");
		Socket socket = null;
	
		try {
			socket = server.accept();
			String readline;
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("client:"+in.readLine());
			readline = br.readLine();
			while(!readline.equals("end")){
				pw.println(readline);
				pw.flush();
				System.out.println("client" +in.readLine());
				System.out.println("server"+readline);
				readline = br.readLine();
			}
			br.close();
			in.close();
			socket.close();
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
