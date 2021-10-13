package com.live.gateway.config;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/13 14:12
 */
public class ProxySystemConfig {

    private static final String COM_LIVE_REMOTING_CLIENT_CONNECT_TIMEOUT = "com.live.gateway.remoting.client.connect.timeout";

    public static int connectTimeoutMillis = Integer.parseInt(System.getProperty(COM_LIVE_REMOTING_CLIENT_CONNECT_TIMEOUT, "3000"));

}
