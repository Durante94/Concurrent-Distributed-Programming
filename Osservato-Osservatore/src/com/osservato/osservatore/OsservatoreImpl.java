package com.osservato.osservatore;

public class OsservatoreImpl implements Osservatore {
    private String id;
    private final int eventLimit;
    private final IntHolder eventNumber;
    private final OsservatoImpl observed;

    public OsservatoreImpl(String id, int eventLimit, OsservatoImpl observed) {
        this.id = id;
        this.eventLimit = eventLimit;
        this.eventNumber = new IntHolder(0);
        this.observed = observed;
        this.observed.registraOsservatore(this);
    }

    @Override
    public void callback(Evento event) {
        synchronized (eventNumber) {
            System.out.println("callback received of event <" + event.getId() + "> by observer <" + id + "> ");
            if (eventNumber.getValue() > eventLimit)
                return;
            eventNumber.incValue();
            if (this.eventNumber.getValue() == eventLimit) {
                System.out.println("Received " + eventNumber.getValue() + "/" + eventLimit);
                eventNumber.setValue(0);
                System.out.println("Observer  <" + id + "> requesting to deregister <" + id + "> ");
                observed.rimuoviOsservatore(this);
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
