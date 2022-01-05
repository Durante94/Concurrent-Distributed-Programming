package com.fabrizio.durante.proxy.esecuzione.remota.utils;

import java.io.Serializable;

public abstract class IResult implements Serializable {
    protected String id;
    protected boolean valid;

    public IResult(String id) {
        this.id = id;
        this.valid = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid() {
        this.valid = true;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
