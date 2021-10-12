package com.livedemo.livegateway;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.rtsp.RtspResponseStatuses;
import io.netty.handler.codec.rtsp.RtspVersions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class ProxyAuthenticationHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("Client {} connected.", ctx.channel().remoteAddress());
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof DefaultHttpRequest) {
//            DefaultHttpRequest httpRequest = (DefaultHttpRequest) msg;
//            log.debug("Http Request Received");
//            HttpHeaders headers = httpRequest.headers();
//            String cookies = headers.get("Cookie");
//            log.debug("Cookies: {}", cookies);
//            if (StringUtils.isBlank(cookies)) {
//                ctx.pipeline().addFirst("httpResponseEncoder", new HttpResponseEncoder());
//                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.FORBIDDEN);
//                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//            } else {
//                ctx.fireChannelRead(msg);
//            }

            ctx.pipeline().remove("httpRequestDecoder");
            ctx.fireChannelRead(msg);
        }

        ctx.fireChannelRead(msg);
    }
}
