package com.fabrizio.durante.proxy.esecuzione.remota.client;

import com.fabrizio.durante.proxy.esecuzione.remota.utils.*;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Author Fabrizio Durante
 * 29/12/2021 11:17
 */
public class Client {
    private static boolean execute = true;

    private static String retrieveId(Map<String, Result> requests, BufferedReader reader) {
        String input;
        boolean checkPrevRequest;

        System.out.println("Would you check the state of the previous request? (Y/any)");
        try {
            input = reader.readLine();
            checkPrevRequest = input.compareToIgnoreCase("Y") == 0;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        if (checkPrevRequest) {
            System.out.println("Choose the request: ");
            int i = 1;
            for (Map.Entry<String, Result> entry : requests.entrySet()) {
                System.out.println((i++) + ") " + entry.getKey());
            }

            try {
                input = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                input = "";
            }

            if (!requests.containsKey(input))
                input = "";
        } else {
            System.out.println("Give me an id:");
            try {
                input = reader.readLine();
                requests.put(input, null);
            } catch (IOException e) {
                e.printStackTrace();
                input = "";
            }
        }

        return input;
    }

    public static void main(String[] args) {
        Map<String, Result> requests = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Stop the execution? (Y/any)");
            String input = null;

            try {
                input = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            if (input.compareToIgnoreCase("Y") == 0)
                execute = false;
            else {
                Request request;
                boolean async;

                input = retrieveId(requests, reader);

                if (input.isEmpty()) {
                    System.out.println("Richiesta non valida");
                    continue;
                }

                System.out.println("The request should be asyncronus?");
                try {
                    String tmp = reader.readLine();
                    async = Boolean.parseBoolean(tmp);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                if (async)
                    request = new Request(input, async);
                else {
                    long timeout;

                    System.out.println("How long do you want to wait for the response? (ms number, 0 or less means that you will wait until the server completes is execution)");
                    try {
                        String tmp = reader.readLine();
                        timeout = Long.parseLong(tmp);
                    } catch (IOException | NumberFormatException e) {
                        e.printStackTrace();
                        continue;
                    }

                    request = new Request(input, async, timeout);
                }

                Result ack = null;
                try {
                    Socket s = new Socket("localhost", 100);
                    ObjectOutputStream noos = new ObjectOutputStream(s.getOutputStream());
                    ObjectInputStream nois = new ObjectInputStream(s.getInputStream());

                    noos.writeObject(request);
                    noos.flush();
                    ack = (Result) nois.readObject();
                } catch (IOException | ClassNotFoundException ioe) {
                    ioe.printStackTrace();
                }

                if (ack == null)
                    System.out.println("Response: null");
                else
                    System.out.println("Response:\n" + ack);
            }
        } while (execute);
    }
}
