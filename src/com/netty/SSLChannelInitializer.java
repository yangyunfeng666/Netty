package com.netty;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLEngine;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;

/**
 * 添加ssl证书认证
 * @author yangyunfeng
 *
 */
public class SSLChannelInitializer  extends ChannelInitializer<SocketChannel>{

	private SslContext sslContext;
	public SSLChannelInitializer()  {
		// TODO Auto-generated constructor stub
		try{
			String keyStorePath = "./file/client.cer";
			String keyPasswd = "yang123456";
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			keystore.load(new FileInputStream(new File(keyStorePath)), keyPasswd.toCharArray());
			KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			factory.init(keystore, keyPasswd.toCharArray());
			sslContext = SslContextBuilder.forServer(factory).build();
		}catch(Exception e){
			
		}
	
	}
	
	@Override
	protected void initChannel(SocketChannel socketchannel) throws Exception {
		// TODO Auto-generated method stub
		SSLEngine sslEngine = sslContext.newEngine(socketchannel.alloc());
		socketchannel.pipeline()
                .addLast(new SslHandler(sslEngine))
                  .addLast("decoder", new HttpRequestDecoder())
                  .addLast("encoder", new HttpResponseEncoder())
                  .addLast("aggregator", new HttpObjectAggregator(512 * 1024))
                  .addLast("handler", new NettyHttpHandler());
	}

}
