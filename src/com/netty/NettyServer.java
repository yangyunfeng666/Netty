package com.netty;

import io.netty.bootstrap.ServerBootstrap;
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

public class NettyServer {

	private int port;

	public NettyServer(int port) {
		super();
		this.port = port;
	}
	
	public void run() throws InterruptedException{
		EventLoopGroup group = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(group).channel(NioServerSocketChannel.class).childHandler(
				new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel chl) throws Exception {
				// TODO Auto-generated method stub
				//添加 解码的decoder 和编码的
				chl.pipeline().addLast("decoder",new HttpRequestDecoder())
				.addLast("encoder",new HttpResponseEncoder())
				.addLast("aggregator", new HttpObjectAggregator(512*1024))//聚会器 消息集合大小 512k fullrequestHander 聚合整个请求消息
				.addLast("handler",new NettyHttpHandler()); 
				
				
			}
				
		}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
		
		//替换成自己的sslhander 添加ssl证书验证
//		b.group(group).channel(NioServerSocketChannel.class).childHandler(
//				new SSLChannelInitializer())
//		.option(ChannelOption.SO_BACKLOG, 128);
		
		ChannelFuture f =b.bind(port).sync();
		f.channel().closeFuture().sync();
		
	}
	
	public static void main(String[] args) {
		try {
			new NettyServer(8080).run();
			System.out.println(" NettyServer 启动");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
