package com.live.gateway.remoting;

import com.live.gateway.config.ProxyConfig;
import com.live.gateway.handler.ProxyBackendHandler;
import com.live.gateway.model.NetAddress;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/11 13:57
 */
public class RemotingClientBootstrap {

    public static ChannelFuture connect(ChannelHandlerContext ctx, NetAddress netAddress) {
        final Channel inboundChannel = ctx.channel();
        Bootstrap b = new Bootstrap();
        b.group(inboundChannel.eventLoop())
                .channel(ctx.channel().getClass())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, ProxyConfig.getInstance().getRemotingClientConfig().getConnectTimeoutMillis())
                .option(ChannelOption.AUTO_READ, false)
                .handler(new ProxyBackendHandler(inboundChannel));
        ChannelFuture f = b.connect(netAddress.getHost(), netAddress.getPort());
        return f;
    }

}
