package com.livedemo.livegateway;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/11 15:13
 */
public class HttpServerHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HttpServerHandler2...channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("HttpServerHandler2->channelRead");
    }

}
