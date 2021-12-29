package com.fabrizio.durante.proxy.esecuzione.remota.utils;

import java.io.Serializable;

/**
 * Author Fabrizio Durante
 * 29/12/2021 10:41
 */
public class Request extends IResult implements Serializable {

    private boolean async;
    private long timeout;

    public Request(String id, boolean async, long timeout) {
        super(id);
        this.async = async;
        this.timeout = timeout;
    }

    public Request(String id, boolean async) {
        this(id, async, 0);
    }

    public boolean isAsync() {
        return async;
    }

    public long getTimeout() {
        return timeout;
    }
}
