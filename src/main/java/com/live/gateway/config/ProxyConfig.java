package com.live.gateway.config;

import com.live.gateway.exception.ProxyException;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/13 16:02
 */
public final class ProxyConfig {

    private static ProxyConfig singleton = null;

    private ProxyServerConfig serverConfig;
    private RemotingServerConfig remotingServerConfig;
    private RemotingClientConfig remotingClientConfig;

    private ProxyConfig(ProxyServerConfig serverConfig, RemotingServerConfig remotingServerConfig, RemotingClientConfig remotingClientConfig) {
        this.serverConfig = serverConfig;
        this.remotingServerConfig = remotingServerConfig;
        this.remotingClientConfig = remotingClientConfig;
    }

    public static ProxyConfig getInstance() {
        if (singleton == null) {
            throw new ProxyException("Use ProxyConfig instance, you must call init method first.");
        }

        return singleton;
    }

    public synchronized static void init(ProxyServerConfig serverConfig, RemotingServerConfig remotingServerConfig, RemotingClientConfig remotingClientConfig) {
        if (singleton != null) {
            throw new ProxyException("ProxyConfig already had been initialized.");
        }

        singleton = new ProxyConfig(serverConfig, remotingServerConfig, remotingClientConfig);
    }

    public ProxyServerConfig getServerConfig() {
        return serverConfig;
    }

    public RemotingServerConfig getRemotingServerConfig() {
        return remotingServerConfig;
    }

    public RemotingClientConfig getRemotingClientConfig() {
        return remotingClientConfig;
    }

}
