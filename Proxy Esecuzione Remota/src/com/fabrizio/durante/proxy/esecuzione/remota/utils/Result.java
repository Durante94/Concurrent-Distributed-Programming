package com.fabrizio.durante.proxy.esecuzione.remota.utils;

import java.io.Serializable;

/**
 * Author Fabrizio Durante
 * 27/12/2021 12:16
 */
public class Result implements Serializable {

    private String id;

    public Result(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Result{" +
                "id='" + id + '\'' +
                '}';
    }
}
