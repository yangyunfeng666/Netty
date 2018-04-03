package com.netty.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Client {
	
	
		
	
	
	public static void main(String[] args){
		
		Bootstrap boot = new Bootstrap();
		EventLoopGroup group = new NioEventLoopGroup();
		try {
	
		boot.group(group).channel(NioSocketChannel.class)
		.handler(new ChannelInitializer<SocketChannel>() {
		

			@Override
			protected void initChannel(SocketChannel chanel) throws Exception {
				// TODO Auto-generated method stub
				ChannelPipeline popeline = chanel.pipeline();
				

		        /**
		         * 这个地方的 必须和服务端对应上。否则无法正常解码和编码
		         */
		        // 以("\n")为结尾分割的 解码器
				popeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))

				.addLast("decoder",new StringDecoder())
				.addLast("encoder",new StringEncoder())
				.addLast("handler",new ClientHander());
				
			}
		});
		
		Channel ch =boot.connect("localhost", 3020).channel();
		 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		 while(true) {
				String ss = in.readLine();
				if(ss==null) {
					continue;
				}
				ch.writeAndFlush(ss+"\r\n");
		 }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				group.shutdownGracefully();
			}
			
		 }
		
		
	}


