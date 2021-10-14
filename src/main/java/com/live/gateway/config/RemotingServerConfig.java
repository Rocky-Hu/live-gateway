package com.live.gateway.config;

import lombok.Data;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/13 14:04
 */
@Data
public class RemotingServerConfig extends AbstractConfig {

    private String host;
    private int port;
    private String appName;
    private String apiKey;
    private String uri;

    @Override
    public String getConfigPrefix() {
        return "remoting.server.";
    }
}
