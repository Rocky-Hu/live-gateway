package com.live.gateway.initializer;

import com.live.gateway.handler.ProxyFrontendAuthHandler;
import com.live.gateway.handler.ProxyFrontendHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/11 13:57
 */
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
        pipeline.addLast(new HttpObjectAggregator(1048576));
        pipeline.addLast("proxyFrontendAuthHandler", new ProxyFrontendAuthHandler());
        pipeline.addLast("proxyFrontendHandler", new ProxyFrontendHandler(remoteHost, remotePort));
    }
}
