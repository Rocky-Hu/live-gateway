package com.live.gateway.exception;

/**
 * @Author 胡学汪
 * @Description
 * @Date 创建于 2021/10/13 15:44
 */
public class ProxyException extends RuntimeException {

    public ProxyException() {
        super();
    }

    public ProxyException(String message) {
        super(message);
    }

    public ProxyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProxyException(Throwable cause) {
        super(cause);
    }

    protected ProxyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
