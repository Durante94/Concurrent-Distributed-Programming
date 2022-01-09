package com.fsm.concorrenti;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Machine> machines = new HashMap<>();
        machines.put("1", new Machine("1", "test_protocol"));
        Core core = new Core(machines);
        new Thread(new Worker(core)).start();
        new Thread(new Worker(core)).start();
        new Thread(new Worker(core)).start();
        new Thread(new PacketGenerator(core)).start();
        new Thread(new PacketGenerator(core)).start();
        new Thread(new PacketGenerator(core)).start();
    }
}
