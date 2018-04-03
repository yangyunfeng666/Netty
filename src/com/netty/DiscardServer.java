package com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {

	private int port;

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public DiscardServer(int port) {
		super();
		this.port = port;
	}
	
	public void run(){
		//netty提供了不同的EventLoopGroup来处理不同的协议， bossGroup 来接收链接  接收链接消息后注册给workGroup 
		//
		//boss 接收服务器端过来的链接 通过构造方法来确认他们的关系
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		System.out.println("运行端口："+port);
		//这是一个NIO服务辅助的启动类，你可以在服务中直接使用channel
		ServerBootstrap b = new ServerBootstrap();
		//指明他们的关系
		b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
		//告诉channel 如何获取链接  NioServerSocketChannel 是以NIO的Selector 为基础实现的连接
		
	
		//处理最近已接收的channerl ChannelInitializer是一个特殊的处理类，
		//他的目的是帮助使用者配置一个新的Channel
		//也许你想通过增加一些处理类比如NettyServerHandler来配置一个新的Channel
		//此处指定的处理程序将始终由新接受的通道计算。 ChannelInitializer是一个特殊的处理程序，
		//用于帮助用户配置新的通道。 很可能要通过添加一些处理程序(例如DiscardServerHandler)来配置新通道的ChannelPipeline来实现您的网络应用程序。
		//随着应用程序变得复杂，可能会向管道中添加更多处理程序，并最终将此匿名类提取到顶级类中。
		.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel chl) throws Exception {
				// TODO Auto-generated method stub
				chl.pipeline().addLast(new DicardServerHander());
				//处理消息信道
			}
		})
		//接受传入的连接的NioServerSocketChannel 
		.option(ChannelOption.SO_BACKLOG, 128)
		//保持 用于由父ServerChannel接受的通道 在这个示例中为NioServerSocketChannel。
		.childOption(ChannelOption.SO_KEEPALIVE, true);
		
		try {
			//绑定断开，然后让链接进来
			  // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
		ChannelFuture f =	b.bind(port).sync();
		//这里会等待一会，直到socket关闭
		f.channel().closeFuture().sync();
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}finally{
			//关闭
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
	}
	
	public static void main(String[] args) {
		int port;
		if(args.length>0){
			port = Integer.parseInt(args[0]);
		}else{
			port = 8080;
		}
		new DiscardServer(port).run();
		System.out.println("server:run()");
	}
	
}
