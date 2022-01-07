package com.osservato.osservatore;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        ArrayList<OsservatoreImpl> observers = new ArrayList<OsservatoreImpl>();
        OsservatoImpl observed = new OsservatoImpl();
        int randomNumber;
        new Thread(new EventGenerator(observed)).start();
        new Thread(new EventGenerator(observed)).start();
        for (int i = 0; i < 10; i++) {
            randomNumber = ThreadLocalRandom.current().nextInt(11, 19 + 1);
            observed.registraOsservatore(
                    new OsservatoreImpl(Thread.currentThread().getName() + " - " + i, randomNumber, observed));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
