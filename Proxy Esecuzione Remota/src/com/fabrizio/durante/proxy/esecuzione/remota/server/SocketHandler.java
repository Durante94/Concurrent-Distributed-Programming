package com.fabrizio.durante.proxy.esecuzione.remota.server;

import com.fabrizio.durante.proxy.esecuzione.remota.utils.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Author Fabrizio Durante
 * 05/01/2022 14:30
 */
public class SocketHandler implements Runnable {
    private Map<String, Result> resultsContainer;
    private ExecutorService threadPool;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public SocketHandler(Map<String, Result> resultsContainer, ExecutorService threadPool, Socket socket) {
        this.resultsContainer = resultsContainer;
        this.threadPool = threadPool;
        this.socket = socket;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            String id;
            try {
                id = ois.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            threadPool.execute(new Executor(resultsContainer, id, oos));
            threadPool.execute(new Servant(resultsContainer, id));
        }
    }
}
