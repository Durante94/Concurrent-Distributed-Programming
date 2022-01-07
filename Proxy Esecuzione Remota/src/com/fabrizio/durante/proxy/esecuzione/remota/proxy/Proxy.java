package com.fabrizio.durante.proxy.esecuzione.remota.proxy;

import com.fabrizio.durante.proxy.esecuzione.remota.utils.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Author Fabrizio Durante
 * 04/01/2022 11:46
 */
public class Proxy implements Runnable {
    private static boolean running;

    private final Map<String, Result> results;
    private Socket serverSocket;
    private ServerSocket proxySocket;

    public Proxy(Socket socket, int proxySocketPort) {
        this.results = new HashMap<>();
        this.serverSocket = socket;
        try {
            this.proxySocket = new ServerSocket(proxySocketPort);
        } catch (IOException e) {
            e.printStackTrace();
            running = false;
        }
    }

    public static void main(String[] argv) {
        if (argv.length == 2)
            try {
                Socket s = new Socket(argv[0], Integer.parseInt(argv[1]));
                running = true;
                new Thread(new Proxy(s, 150)).start();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Errore apertura server socket");
            }
        else
            System.out.println("Indicare host e porta");

        
    }

    public String init() throws IOException {
        if (!running) throw new IOException("Non posso connettermi al server");

        Result service = new Result(java.util.UUID.randomUUID().toString());

        synchronized (results) {
            results.put(service.getId(), service);
        }

        try {
            new ObjectOutputStream(serverSocket.getOutputStream()).writeUTF(service.getId());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return service.getId();
    }

    public Result asyncRequest(String id) {
        Result toReturn = null;

        synchronized (results) {
            toReturn = results.get(id);
        }
        synchronized (toReturn) {
            if (!toReturn.isValid())
                toReturn = null;
            else
                toReturn.setRequestType("Async");

            return toReturn;
        }
    }

    public Result syncRequest(String id) {
        Result toReturn = null;

        synchronized (results) {
            toReturn = results.get(id);
        }

        synchronized (toReturn) {
            while (!toReturn.isValid()) {
                try {
                    toReturn.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            toReturn.setRequestType("Sync");
            return toReturn;
        }
    }

    public Result syncRequest(String id, long timeout) {
        Result toReturn = null;

        synchronized (results) {
            toReturn = results.get(id);
        }

        synchronized (toReturn) {
            long limit = System.currentTimeMillis() + timeout;
            while (!toReturn.isValid()) {
                try {
                    toReturn.wait(timeout);
                    if (System.currentTimeMillis() >= limit)
                        return null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return toReturn;
        }
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(serverSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            running = false;
        }

        while (running) {
            Result fromServer = null, localResult = null;

            try {
                fromServer = (Result) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                running = false;
                continue;
            }

            synchronized (results) {
                localResult = results.get(fromServer.getId());
            }

            synchronized (localResult) {
                localResult.setValid(fromServer.isValid());
                localResult.notifyAll();
            }
        }
    }
}
