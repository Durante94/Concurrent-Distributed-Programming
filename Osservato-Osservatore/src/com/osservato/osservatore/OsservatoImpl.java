package com.osservato.osservatore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OsservatoImpl implements Osservato {

    private final HashMap<String, OsservatoreImpl> observers;

    public OsservatoImpl(HashMap<String, OsservatoreImpl> observers) {
        this.observers = observers;
    }

    public OsservatoImpl() {
        this.observers = new HashMap<>();
    }

    @Override
    public void registraOsservatore(OsservatoreImpl observer) {
        synchronized (observers) {
            if (this.observers.containsKey(observer.getId())) {
                return;
            }
            this.observers.put(observer.getId(), observer);
            System.out.println("added observer <" + observer + "> to observers list");
        }
    }

    @Override
    public void rimuoviOsservatore(OsservatoreImpl observer) {
        synchronized (observers) {
            this.observers.remove(observer.getId());
            System.out.println("removed observer <" + observer + "> from observers list");
        }
    }

    @Override
    public void nuovoEvento(Evento event) {
        HashMap<String, OsservatoreImpl> shallowCopy;
        OsservatoreImpl observer;
        synchronized (observers) {
            shallowCopy = (HashMap<String, OsservatoreImpl>) observers.clone();
            System.out.println("added event <" + event.getId() + "> ");
            for (Map.Entry<String, OsservatoreImpl> entry : shallowCopy.entrySet()) {
                observer = entry.getValue();
                observer.callback(event);
            }
        }
    }
}
