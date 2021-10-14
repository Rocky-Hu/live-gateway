package com.live.gateway.handler;

import com.live.gateway.config.ProxyConfig;
import com.live.gateway.constants.ProxyConstants;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/11 13:57
 */
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

            // 请求认证
            if (ProxyConfig.getInstance().getServerConfig().isEnableAuth()) {
                HttpHeaders headers = httpRequest.headers();
                String cookies = headers.get("Cookie");
                log.debug("Client Request Cookies: {}", cookies);
                if (StringUtils.isBlank(cookies)) {
                    log.warn("Auth failed, invalid cookie, the inbound channel will be closed: {}", ctx.channel());
                    forbiddenResponse(ctx);
                    return;
                }
            }

            // 请求URI校验
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(httpRequest.uri(), CharsetUtil.UTF_8);
            String path = queryStringDecoder.path();
            Map<String, List<String>> parameters = queryStringDecoder.parameters();
            if (!path.startsWith(ProxyConfig.getInstance().getServerConfig().getFilteredUri())) {
                log.warn("Auth failed, invalid request path, the inbound channel will be closed: {}", ctx.channel());
                forbiddenResponse(ctx);
                return;
            }
            if (parameters.get(ProxyConstants.QUERY_ID_PARAMETER) == null || parameters.get(ProxyConstants.QUERY_ID_PARAMETER).size() == 0) {
                log.warn("Auth failed, invalid request parameter, the inbound channel will be closed: {}", ctx.channel());
                forbiddenResponse(ctx);
                return;
            }

            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.debug("channelReadComplete: channel-> {}", ctx.channel());
    }

    private void forbiddenResponse(ChannelHandlerContext ctx) {
        ctx.pipeline().addBefore("proxyFrontendAuthHandler", "httpResponseEncoder", new HttpResponseEncoder());
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.FORBIDDEN);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
