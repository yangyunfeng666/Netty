package com.netty.test;



import java.nio.channels.Channels;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ServerHander extends SimpleChannelInboundHandler<String>{

	//自动移除channel
	private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String arg1) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("服务器收到数据:"+arg1);
		Channel incomme = ctx.channel();
		//ctx.writeAndFlush("hello server return the data to you data:"+arg1+"\n");
		System.out.println("客户端数据个数"+channels.size());
		for(Channel cha:channels) {
			if(cha!=incomme) {
			cha.writeAndFlush(ctx.channel().remoteAddress()+"say:"+arg1+"\n");
			}else {
			cha.writeAndFlush("you say:"+arg1+"\n");
			}
		}
	}
	
	/**
	 * 每次连接创建时候 触发
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("已经创建一个连接地址是"+ctx.channel().remoteAddress()+"在线");
		ctx.channel().writeAndFlush("数据已经连接服务器\n");
		super.channelActive(ctx);
		
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("已经创建一个连接地址是"+ctx.channel().remoteAddress()+"active");
		ctx.channel().writeAndFlush(""+ctx.channel().remoteAddress()+"加入\n");
		Channel channel = ctx.channel();
		channels.add(channel);
		super.handlerAdded(ctx);
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.channel().writeAndFlush(""+ctx.channel().remoteAddress()+"离开\n");
		super.handlerRemoved(ctx);
	}
	
	/**
	 * 连接断开时候触发
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(ctx.channel().remoteAddress()+"掉线\n");
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("出现错误 exceptionCaught");
		super.exceptionCaught(ctx, cause);
	}

}
