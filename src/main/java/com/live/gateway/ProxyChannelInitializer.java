package com.live.gateway;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;

public class ProxyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final String remoteHost;
    private final int remotePort;

    public ProxyChannelInitializer(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpRequestEncoder());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new ProxyFrontendAuthHandler());
        pipeline.addLast(new ProxyFrontendHandler(remoteHost, remotePort));
    }
}
