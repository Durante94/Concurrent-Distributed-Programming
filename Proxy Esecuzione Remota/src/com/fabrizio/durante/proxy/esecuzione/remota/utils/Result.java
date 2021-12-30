package com.fabrizio.durante.proxy.esecuzione.remota.utils;

import java.io.Serializable;

/**
 * Author Fabrizio Durante
 * 27/12/2021 12:16
 */
public class Result extends IResult implements Serializable {

    private String requestType;

    public Result(String id, String requestType) {
        super(id);
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id='" + id + '\'' +
                ", requestType='" + requestType + '\'' +
                '}';
    }
}

