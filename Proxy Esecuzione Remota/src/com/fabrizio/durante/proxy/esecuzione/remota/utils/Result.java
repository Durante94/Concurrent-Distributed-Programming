package com.fabrizio.durante.proxy.esecuzione.remota.utils;

import java.io.Serializable;

/**
 * Author Fabrizio Durante
 * 27/12/2021 12:16
 */
public class Result extends IResult implements Serializable {

    private String requestType;

    public Result(String id) {
        super(id);
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id='" + id + '\'' +
                ", valid=" + valid +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}

