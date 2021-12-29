package com.fabrizio.durante.proxy.esecuzione.remota.server;

/**
 * Author Fabrizio Durante
 * 27/12/2021 13:00
 */
public class Computation implements Runnable{
    private long duration;

    public Computation(long duration) {
        this.duration = duration;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
