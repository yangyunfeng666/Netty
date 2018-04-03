package com.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

//解码器  简单的实现
public class LiveMessgeDecoder extends ByteToMessageDecoder {
	private int HEAD_LENGH = 4;//如果他的头部定义大小为4

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		if(in.readableBytes()<HEAD_LENGH){
			return ;
		}
		in.markReaderIndex();//标记readIndex 位置
		int dataLength = in.readInt();//读取消息的长度 这里会让readIndex +4
		if(dataLength<0){//如果长度为0那么就返回
			return ;
		}
		if(in.readableBytes()<dataLength){//如果数据小于长度 那么返回到标记的位置去
			in.resetReaderIndex();
			return ;
		}
		byte[] bytes = new byte[dataLength];
		in.readBytes(bytes);//读取数据
		Object o = convertToObject(bytes);//自己定义的序列化方法
		out.add(o);
	}

	private Object convertToObject(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}
}
