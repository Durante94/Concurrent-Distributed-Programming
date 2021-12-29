package com.fabrizio.durante.proxy.esecuzione.remota.utils;

import java.io.Serializable;

public abstract class IResult implements Serializable {
    String id;

    public IResult(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
