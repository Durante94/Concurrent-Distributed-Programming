package com.fabrizio.durante.proxy.esecuzione.remota.server;

import com.fabrizio.durante.proxy.esecuzione.remota.utils.Request;
import com.fabrizio.durante.proxy.esecuzione.remota.utils.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * Author Fabrizio Durante
 * 27/12/2021 11:43
 */
public class ServerAction implements Runnable {
    private Socket socket;
    private Map<String, Thread> resultContainer;

    public ServerAction(Socket socket, Map<String, Thread> resultContainer) {
        this.socket = socket;
        this.resultContainer = resultContainer;
    }

    @Override
    public void run() {
        Result toReturn = null;
        Thread execution = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        Request request = null;

        try {
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return;
        }

        try {
            request = (Request) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            try {
                ois.close();
                oos.close();
                socket.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return;
        }

        synchronized (resultContainer) {
            if (resultContainer.containsKey(request.getId())) {
                execution = resultContainer.get(request.getId());
            } else {
                execution = new Thread(new Computation(10 * 60 * 1000));
                resultContainer.put(request.getId(), execution);
            }
        }

        if (request.isAsync())
            toReturn = getResultAsync(request.getId(), execution);
        else if (request.getTimeout() > 0)
            toReturn = getResult(request.getId(), execution, request.getTimeout());
        else
            toReturn = getResult(request.getId(), execution);

        try {
            oos.writeObject(toReturn);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                ois.close();
                oos.close();
                socket.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return;
        }

        try {
            ois.close();
            oos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized Result getResultAsync(String id, Thread execution) {
        Result toReturn;
        if (execution.getState() == Thread.State.TERMINATED)
            toReturn = new Result(id);
        else
            toReturn = null;

        return toReturn;
    }

    private synchronized Result getResult(String id, Thread execution) {
        try {
            execution.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        return new Result(id);
    }

    private synchronized Result getResult(String id, Thread execution, long timeout) {
        try {
            execution.join(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        if (execution.getState() == Thread.State.TERMINATED)
            return new Result(id);
        else
            return null;
    }
}
