package com.live.gateway;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class ProxyFrontendAuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("Client {} connected.", ctx.channel().remoteAddress());
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("channelRead: channel-> {}, msg-> {}", ctx.channel(), msg);
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            HttpHeaders headers = httpRequest.headers();
            String cookies = headers.get("Cookie");
            log.debug("Cookies: {}", cookies);
            if (StringUtils.isBlank(cookies)) {
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.FORBIDDEN);
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                ctx.fireChannelRead(msg);
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.debug("channelReadComplete: channel-> {}", ctx.channel());
    }

}
