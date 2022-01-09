package com.fsm.concorrenti;

public class Machine {
    private String id;
    private String protocol;
    private boolean isBusy;

    public Machine(String id, String protocol) {
        this.id = id;
        this.protocol = protocol;
        this.isBusy = true;
        System.out.println(this.toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public void process(Packet p) {
        System.out.println("Started processing " + p.toString());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished processing " + p.toString());
    }

    @Override
    public String toString() {
        return "Machine{" +
                "id='" + id + '\'' +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
