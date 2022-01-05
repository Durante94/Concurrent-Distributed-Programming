package com.fabrizio.durante.proxy.esecuzione.remota.server;

import com.fabrizio.durante.proxy.esecuzione.remota.utils.Result;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

/**
 * Author Fabrizio Durante
 * 04/01/2022 12:47
 */
public class Executor implements Runnable {
    private final Map<String, Result> resultsContainer;
    private final String id;
    private final ObjectOutputStream oos;

    public Executor(Map<String, Result> resultsContainer, String id, ObjectOutputStream oos) {
        this.resultsContainer = resultsContainer;
        this.id = id;
        this.oos = oos;
    }

    @Override
    public void run() {
        Result ref;

        synchronized (resultsContainer) {
            while (!resultsContainer.containsKey(id)) {
                try {
                    resultsContainer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ref = resultsContainer.get(id);
        }

        synchronized (ref) {
            ref.setValid();
            try {
                oos.writeObject(ref);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
