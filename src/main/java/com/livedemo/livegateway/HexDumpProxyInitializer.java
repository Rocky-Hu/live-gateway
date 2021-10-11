package com.livedemo.livegateway;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;

public class HexDumpProxyInitializer extends ChannelInitializer<SocketChannel> {

    private final String remoteHost;
    private final int remotePort;

    public HexDumpProxyInitializer(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpServerHandler1());
        pipeline.addLast(new HttpServerHandler2());
//        pipeline.addLast(new HexDumpProxyFrontendHandler(remoteHost, remotePort));
//        ch.pipeline().addLast(
//                new LoggingHandler(LogLevel.INFO),
//                new HexDumpProxyFrontendHandler(remoteHost, remotePort));
    }
}
