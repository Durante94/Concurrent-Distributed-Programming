package com.fabrizio.durante.proxy.esecuzione.remota.server;

import com.fabrizio.durante.proxy.esecuzione.remota.utils.Result;

import java.util.Map;

/**
 * Author Fabrizio Durante
 * 27/12/2021 11:43
 */
public class Servant implements Runnable {
    private final Map<String, Result> resultsContainer;
    private final String id;

    public Servant(Map<String, Result> resultsContainer, String id) {
        this.id = id;
        this.resultsContainer = resultsContainer;
    }

    @Override
    public void run() {
        synchronized (resultsContainer) {
            if (!resultsContainer.containsKey(id))
                resultsContainer.put(id, new Result(id));
            resultsContainer.notifyAll();
        }
    }
}
