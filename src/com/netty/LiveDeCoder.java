package com.netty;

import java.util.List;

import com.netty.socket.LiveMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

//继承ReplayingDecoder，泛型LiveState，用来表示当前读取的状态
/*
描述LiveState，有读取长度和读取内容两个状态
1初始化的时候设置为读取长度的状态
2读取的时候通过state()方法来确定当前处于什么状态
3如果读取出来的长度大于0，则设置为读取内容状态，下一次读取的时候则从这个位置开始
4读取完成，往结果里面放解析好的数据

*/
public class LiveDeCoder extends ReplayingDecoder<LiveDeCoder.LiveState>{

	//设置一个常年设置
	public  enum LiveState{
		 LENGTH,CONTENT
	}
	
	private LiveMessage mMessage = new LiveMessage();
	
	 LiveDeCoder(){
		super(LiveState.LENGTH);//1 初始化设置读取长度的状态
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
		// TODO Auto-generated method stub
		switch (state()) {//2
		case LENGTH://如果是长度的话，如果长度大于0那么设置他的内容 进入内容组装阶段  否则添加到数据列表中
			int lenth = buf.readInt();
			if(lenth>0){
				checkpoint(LiveState.CONTENT);//3
			}else{
				list.add(mMessage);
			}
			break;
		case CONTENT://如果是内容状态那么，把他读取出来，然后添加到列表中
			  byte[] bytes = new byte[mMessage.getLength()];
			  buf.readBytes(bytes);
			  String content  = new String(bytes);
			  mMessage.setContent(content);
			  list.add(mMessage);//4
		default:
			 throw new IllegalStateException("invalid state:" + state());
		}
	}
}
