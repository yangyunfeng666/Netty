package com.netty.socket;

import com.netty.NettyHttpHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class SocketServer {

	
	//端口
	private int port;
	
	public SocketServer(int port) {
		super();
		this.port = port;
	}
	
	public void run() throws InterruptedException{
		System.out.println("启动服务");
		EventLoopGroup group = new NioEventLoopGroup();
		ServerBootstrap boot = new ServerBootstrap();
		boot.group(group).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel chl) throws Exception {
				// TODO Auto-generated method stub
				chl.pipeline().addLast("decoder",new SocketDecoder())
				.addLast("encoder", new SocketEncoder())
				.addLast("handler", new SocketHandler());
				
//				chl.pipeline().addLast("decoder",new HttpRequestDecoder())
//				.addLast("encoder",new HttpResponseEncoder())
//				.addLast("aggregator", new HttpObjectAggregator(512*1024))//聚会器 消息集合大小 512k fullrequestHander 聚合整个请求消息
//				.addLast("handler",new NettyHttpHandler());    
				
			}
		}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);	
		
		ChannelFuture f =boot.bind(port).sync();
		f.channel().closeFuture().sync();
	
	}
	
	public static void main(String[] args) {
		try {
			new SocketServer(5230).run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
