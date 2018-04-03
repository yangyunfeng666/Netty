package com.netty.socket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

public class SocketHandler extends SimpleChannelInboundHandler<LiveMessage> {

	private HashMap<Integer, SocketChanelCache> mCacheHashMap = new HashMap<Integer, SocketChanelCache>();

	SocketHandler() {

	}
	
	/**
	 * 每次建立上的情况下打印
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("remote" +ctx.channel().remoteAddress()+ "active");
		ctx.writeAndFlush("welcome "+ctx.channel().localAddress()+"welcome the service");
		super.channelActive(ctx);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LiveMessage msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("channelRead0");
		Channel channel = ctx.channel();
		  final int hashCode = channel.hashCode();
		System.out.println(msg.toString()+"cachesize:"+mCacheHashMap.size()+"  channel code:"+hashCode);
		if (!mCacheHashMap.containsKey(hashCode)) {// 如果没有包含放入缓存中
		    System.out.println("channelCache.containsKey(hashCode), put key:" + hashCode);
			channel.closeFuture().addListener(future -> {//channel 关闭监听
				 System.out.println("channelCache.remove(hashCode), " + hashCode);
				mCacheHashMap.remove(hashCode);
			});

			ScheduledFuture future = ctx.executor().schedule(() -> {
				System.out.println("关闭任务");
				 System.out.println("channel close hashcode " + hashCode);
				channel.close();
			}, 30, TimeUnit.SECONDS);//如果10秒内没有回复则关闭服务
			mCacheHashMap.put(hashCode, new SocketChanelCache(channel, future));
		}

		switch (msg.getType()) {
		case LiveMessage.HEAD_TYPE:
				SocketChanelCache cache = mCacheHashMap.get(hashCode);
				ScheduledFuture future = ctx.executor().schedule(()->{
					 System.out.println("channel close hashcode " + hashCode);
				channel.close();
				}, 30, TimeUnit.SECONDS);
				cache.getScheduleFuture().cancel(true);//把原来的那个想跳关掉 
				cache.setScheduleFuture(future);//重新设置心跳 定时任务
				ctx.channel().writeAndFlush(msg);
			break;
		case LiveMessage.CONTENT_TYPE:
				//遍历所有的人 发送消息
				 mCacheHashMap.entrySet().stream().forEach(action->{
					Channel c = action.getValue().getChannel();
					c.writeAndFlush(msg);
				 });
			break;

		default:
			break;
		}

	}
	

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
		System.out.println("channelReadComplete");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		// super.exceptionCaught(ctx, cause);
		System.out.println("exceptionCaught");
		if (cause != null)
			cause.printStackTrace();
		if (ctx != null)
			ctx.close();
	}

}
