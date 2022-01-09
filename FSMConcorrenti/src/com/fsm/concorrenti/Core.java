package com.fsm.concorrenti;

import java.util.*;

public class Core {
    private final LinkedList<Packet> packets;
    private final HashMap<String, Machine> machines;

    public Core(HashMap<String, Machine> machinesMap) {
        this.packets = new LinkedList<>();
        this.machines = machinesMap;
    }

    public void addPacket(Packet p) {
        synchronized (packets) {
            packets.add(p);
            System.out.println("added " + p.toString());
            packets.notify();
        }
    }

    // TODO: domanda >> è meglio mettere il while(true) dentro o fuori il synchronize? nell'esempio a lezione il while è fuori
    // TODO: domanda >> è giusto sincronizzare su machines (all'interno)?
    public Packet getPacket() {
        Packet p;
        Machine m;
        synchronized (packets) {
            while (true) {
                ListIterator<Packet> iterator = packets.listIterator();
                while (iterator.hasNext()) {
                    p = iterator.next();
                    m = machines.get(p.getId());
                    // instance a new machine if none is present and set it to busy
                    if (m == null) {
                        System.out.println("Adding new machine");
                        m = new Machine(p.getId(), p.getProtocol());
                        machines.put(p.getId(), m);
                        iterator.remove();
                        System.out.println("Remove " + p);
                        return p;
                    }

                    // if the machine was alredy present check if is busy
                    synchronized (machines) {
                        if (!m.isBusy()) {
                            m.setBusy(true);
                            iterator.remove();
                            System.out.println("Remove " + p);
                            return p;
                        }
                    }
                }

                try {
                    packets.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void freeMachine(Machine m) {
        synchronized (machines) {
            m.setBusy(false);
            System.out.println("Freed " + m);
        }
    }

    public Machine getMachine(String machineId) {
        return machines.get(machineId);
    }
}
