package com.fsm.concorrenti;

import java.util.LinkedList;

public class Worker implements Runnable {
    private Core core;

    public Worker(Core core) {
        this.core = core;
    }

    @Override
    public void run() {
        Packet p;
        Machine m;
        while (true) {
            p = core.getPacket();
            System.out.println("Worker " + Thread.currentThread().getName() + " got " + p.toString());
            m = core.getMachine(p.getId());
            m.process(p);
            core.freeMachine(m);
        }
    }
}
