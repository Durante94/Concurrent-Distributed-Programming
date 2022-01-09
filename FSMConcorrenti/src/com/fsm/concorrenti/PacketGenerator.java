package com.fsm.concorrenti;

import java.util.concurrent.ThreadLocalRandom;

public class PacketGenerator implements Runnable {
    private Core core;

    public PacketGenerator(Core core) {
        this.core = core;
    }

    @Override
    public void run() {
        Packet packet;
        int randomNumber;
        while (true) {
            randomNumber = ThreadLocalRandom.current().nextInt(1, 3 + 1);
            packet = new Packet(Integer.toString(randomNumber), "test_protocol", "test_payload");
            System.out.println("sending " + packet.toString());
            core.addPacket(packet);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
