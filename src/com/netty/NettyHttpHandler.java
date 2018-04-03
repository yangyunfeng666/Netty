package com.netty;

import java.net.SocketAddress;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;


//声明 只有 FullHttpRequest 的消息才能过来
public class NettyHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest>  {
	 private AsciiString contentType = HttpHeaderValues.TEXT_PLAIN;

	  

	 
	 
	protected void messageReceived(ChannelHandlerContext chx, FullHttpRequest msg) throws Exception {
		// TODO Auto-generated method stub
		//封装一个response 返回给前端
		System.out.println("getclass" + msg.getClass().getName());
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
				Unpooled.wrappedBuffer("test hello I love You baby".getBytes())); 
		HttpHeaders heads = response.headers();
		heads.add(HttpHeaderNames.CONTENT_TYPE, contentType +";charset=UTF-8");
		heads.addInt(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
		heads.add(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
		chx.writeAndFlush(response);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("channelReadComplete");
		super.channelReadComplete(ctx);
		ctx.flush();
	}
	
	
	//错误处理
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
//		super.exceptionCaught(ctx, cause);
		 System.out.println("exceptionCaught");
		cause.printStackTrace();
		ctx.close();
	}
//
//@Override
//protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
//	// TODO Auto-generated method stub
//	System.out.println("getclass" + msg.getClass().getName());
//	DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
//			Unpooled.wrappedBuffer("test hello I love You baby".getBytes())); 
//	HttpHeaders heads = response.headers();
//	heads.add(HttpHeaderNames.CONTENT_TYPE, contentType +";charset=UTF-8");
//	heads.addInt(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
//	heads.add(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
//	ctx.writeAndFlush(response);
//}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("getclass" + msg.getClass().getName());
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
				Unpooled.wrappedBuffer("test hello I love You baby".getBytes())); 
		HttpHeaders heads = response.headers();
		heads.add(HttpHeaderNames.CONTENT_TYPE, contentType +";charset=UTF-8");
		heads.addInt(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
		heads.add(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
		ctx.writeAndFlush(response);
	}


}
