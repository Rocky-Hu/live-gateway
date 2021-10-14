package com.live.gateway.initializer;

import com.live.gateway.handler.ProxyFrontendAuthHandler;
import com.live.gateway.handler.ProxyFrontendHandler;
import com.live.gateway.handler.ProxyFrontendTransformHandler;
import com.live.gateway.model.NetAddress;
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

    private NetAddress remoteNetAddress;

    public ProxyChannelInitializer(NetAddress remoteNetAddress) {
       this.remoteNetAddress = remoteNetAddress;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpObjectAggregator(1048576));
        pipeline.addLast("proxyFrontendAuthHandler", new ProxyFrontendAuthHandler());
        pipeline.addLast("proxyFrontendTransformHandler", new ProxyFrontendTransformHandler());
        pipeline.addLast("proxyFrontendHandler", new ProxyFrontendHandler(remoteNetAddress));
    }
}
