package com.netty.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class SocketEncoder extends MessageToByteEncoder<LiveMessage>{

	@Override
	protected void encode(ChannelHandlerContext ctx, LiveMessage msg, ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		out.writeByte(msg.getType());
		out.writeByte(msg.getLength());
		if(msg.getContent()!=null){
			out.writeBytes(msg.getContent().getBytes());
		}
	}

}
