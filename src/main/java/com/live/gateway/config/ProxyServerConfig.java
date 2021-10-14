package com.live.gateway.config;

import lombok.Data;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/13 14:02
 */
@Data
public class ProxyServerConfig extends AbstractConfig {

    private int listenPort = 8888;
    private boolean enableAuth = true;
    private String filteredUri;

    @Override
    public String getConfigPrefix() {
        return "proxy.server.";
    }

}
