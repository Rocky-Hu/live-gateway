package com.live.gateway.model;

import lombok.Data;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/11 13:57
 */
@Data
public class NetAddress {

    private String host;
    private int port;

    public NetAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

}
