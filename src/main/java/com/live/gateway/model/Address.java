package com.live.gateway.model;

import lombok.Data;

@Data
public class Address {

    private String host;
    private int port;

    public Address(String host, int port) {
        this.host = host;
        this.port = port;
    }

}
