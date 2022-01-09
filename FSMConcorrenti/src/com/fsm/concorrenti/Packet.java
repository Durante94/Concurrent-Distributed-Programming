package com.fsm.concorrenti;

public class Packet {
    private String id;
    private String protocol;
    private String payload;

    public Packet(String id, String protocol, String payload) {
        this.id = id;
        this.protocol = protocol;
        this.payload = payload;
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

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "id='" + id + '\'' +
                ", protocol='" + protocol + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
