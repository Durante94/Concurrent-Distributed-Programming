package com.fabrizio.durante.proxy.esecuzione.remota.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread {

    private static boolean acceptConnection;

    private ServerSocket serverSocket;
    private Map<String, Thread> resultContainer;
    private ExecutorService threadPool;

    static {
        acceptConnection = true;
    }

    public Server(int port, PrintStream printStream) {
        int executionCores = Runtime.getRuntime().availableProcessors();
        if (executionCores >= 3)
            executionCores /= 2;
        else
            executionCores = 1;

        try {
            this.serverSocket = new ServerSocket(port);
            resultContainer = new HashMap<>();
            threadPool = Executors.newFixedThreadPool(executionCores);
        } catch (IOException e) {
            e.printStackTrace();
            blockConnection();
        }
    }

    public static void main(String[] args) {
        int port = -1;

        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ne) {
                System.out.println("Porta di ascolto non valida");
                ne.printStackTrace();
                return;
            }
        } else
            port = 100;

        Server server = new Server(port, System.out);

        if (!server.isAcceptConnection()) return;

        System.out.println("Premi invio per fermare l'esecuzione del server");
        server.start();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        try {
            input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        blockConnection();

        try {
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void blockConnection() {
        acceptConnection = false;
    }

    public static boolean isAcceptConnection() {
        return acceptConnection;
    }

    @Override
    public void run() {
        System.out.println("In ascolto sulla porta: " + serverSocket.getLocalPort());

        while (isAcceptConnection()) {
            try {
                Socket socket = serverSocket.accept();
                threadPool.execute(new ServerAction(socket, resultContainer));
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        threadPool.shutdown();

        System.out.println("Server chiuso");
    }
}
