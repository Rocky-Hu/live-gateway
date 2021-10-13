package com.live.gateway.config;

import lombok.Data;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/13 14:10
 */
@Data
public class RemotingClientConfig extends AbstractConfig {

    private int connectTimeoutMillis = ProxySystemConfig.connectTimeoutMillis;

    @Override
    public String getConfigPrefix() {
        return "remoting.client.";
    }
}
