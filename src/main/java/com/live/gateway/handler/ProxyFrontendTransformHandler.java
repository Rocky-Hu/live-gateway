package com.live.gateway.handler;

import com.live.gateway.config.ProxyConfig;
import com.live.gateway.config.RemotingServerConfig;
import com.live.gateway.constants.ProxyConstants;
import com.live.gateway.util.DigestUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/14 17:29
 */
public class ProxyFrontendTransformHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(httpRequest.uri(), CharsetUtil.UTF_8);
            Map<String, List<String>> parameters = queryStringDecoder.parameters();
            String streamName = parameters.get(ProxyConstants.QUERY_ID_PARAMETER).get(0);

            RemotingServerConfig remotingServerConfig = ProxyConfig.getInstance().getRemotingServerConfig();
            StringBuilder sb = new StringBuilder();
            sb.append(remotingServerConfig.getAppName());
            sb.append(remotingServerConfig.getApiKey());
            sb.append(streamName);
            String sign = DigestUtil.md5DigestAsHexString(sb.toString().getBytes(StandardCharsets.UTF_8));

            StringBuilder newSb = new StringBuilder();
            newSb.append(remotingServerConfig.getUri());
            newSb.append("?");
            newSb.append(ProxyConstants.QUERY_APP_NAME_PARAMETER);
            newSb.append("=");
            newSb.append(remotingServerConfig.getAppName());
            newSb.append("&");
            newSb.append(ProxyConstants.QUERY_STREAM_NAME_PARAMETER);
            newSb.append("=");
            newSb.append(streamName);
            newSb.append("&");
            newSb.append(ProxyConstants.QUERY_SIGN_PARAMETER);
            newSb.append("=");
            newSb.append(sign);

            httpRequest.setUri(newSb.toString());
            ctx.fireChannelRead(msg);
        }

    }

}
