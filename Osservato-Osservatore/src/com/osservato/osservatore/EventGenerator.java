package com.osservato.osservatore;

public class EventGenerator implements Runnable {
    private OsservatoImpl observed;

    public EventGenerator(OsservatoImpl obs) {
        this.observed = obs;
    }

    @Override
    public void run() {
        int counter = 0;
        Evento event;
        int sleep_time = (int) ((1f / 5f) * 1000f);
        while (true) {
            try {
                Thread.sleep(sleep_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            event = new Evento(Thread.currentThread().getName() + " - " + counter);
            observed.nuovoEvento(event);
            counter++;
        }
    }
}
