package com.netty.socket;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import io.netty.handler.codec.ReplayingDecoder;

public class SocketDecoder extends  ReplayingDecoder<SocketDecoder.LiveState>{
	
	private LiveMessage mMessage ; 
	public enum LiveState{
		TYPE,LENGTH,CONTENT
	}
	
	SocketDecoder(){
		super(LiveState.TYPE);
	}

	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		LiveState state = state();
		switch (state) {
		case TYPE:
			byte type =	in.readByte();
			mMessage = new LiveMessage();
			mMessage.setType(type);
			checkpoint(LiveState.LENGTH);
			break;
		case LENGTH:
			int length = in.readInt();
			mMessage.setLength(length);
			if(length>0){//如果大于零则设置当前状态是content
				checkpoint(LiveState.CONTENT);
			}else{
				//如果是小于零 则设置当前状态是type
				checkpoint(LiveState.TYPE);
				out.add(mMessage);
			}
			break;
		case CONTENT:
			byte[] bytes = new byte[mMessage.getLength()];
			in.readBytes(bytes);
			String content = new String(bytes);
			mMessage.setContent(content);
			out.add(mMessage);
			checkpoint(LiveState.TYPE);
			break;
		default:
			throw new IllegalStateException("ill error");
		}
	}


}
