package com.live.gateway;

import com.live.gateway.model.Address;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;

public class ProxyBootstrap {

    public static ChannelFuture connect(ChannelHandlerContext ctx, Address address) {
        final Channel inboundChannel = ctx.channel();
        Bootstrap b = new Bootstrap();
        b.group(inboundChannel.eventLoop())
                .channel(ctx.channel().getClass())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.AUTO_READ, false)
                .handler(new ProxyBackendHandler(inboundChannel));
        ChannelFuture f = b.connect(address.getHost(), address.getPort());
        return f;
    }

}
