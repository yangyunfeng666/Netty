package com.netty.socket;

import io.netty.channel.Channel;
import io.netty.util.concurrent.ScheduledFuture;

public class SocketChanelCache {

	private Channel channel;
	private ScheduledFuture scheduleFuture;
	
	public SocketChanelCache(Channel channel, ScheduledFuture scheduleFuture) {
		super();
		this.channel = channel;
		this.scheduleFuture = scheduleFuture;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public ScheduledFuture getScheduleFuture() {
		return scheduleFuture;
	}
	public void setScheduleFuture(ScheduledFuture scheduleFuture) {
		this.scheduleFuture = scheduleFuture;
	}
	
	
}
