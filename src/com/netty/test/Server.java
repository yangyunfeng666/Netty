package com.netty.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {

	public static void main(String[] args) {
		new Server().run();
	}
	
	public void run() {
		
		EventLoopGroup group = new NioEventLoopGroup();
		EventLoopGroup group1 = new NioEventLoopGroup();
		ServerBootstrap boot = new ServerBootstrap();
		boot.group(group1,group)//主从reactor模型
		.channel(NioServerSocketChannel.class)//NIO 模型传输
		.childHandler(new ChannelInitializer<Channel>() {

		@Override
		protected void initChannel(Channel channel) throws Exception {
			// TODO Auto-generated method stub
			channel.pipeline()
			//定义\n分隔符
			.addLast("farmer",new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
			.addLast("decoder",new StringDecoder())
			.addLast("encoder",new StringEncoder())
			.addLast("handler", new ServerHander());
		}
	}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);
	
	try {
		ChannelFuture f =boot.bind(3020).sync();
		f.channel().closeFuture().sync();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
}
