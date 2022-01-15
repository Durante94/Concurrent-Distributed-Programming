package reentrantlock;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Author Fabrizio Durante
 * 11/01/2022 11:01
 */
public class Client implements Runnable {
    private String name;
    private CacheHandler rmiCache;

    public Client(String name, CacheHandler rmiCache) {
        this.name = name;
        this.rmiCache = rmiCache;
    }

    public static void main(String[] args) {
        CacheHandler cacheHandler;
        try {
            cacheHandler = (CacheHandler) Naming.lookup("server");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
            return;
        }

        new Thread(new Client("A", cacheHandler));
        new Thread(new Client("B", cacheHandler));
        new Thread(new Client("C", cacheHandler));
    }

    @Override
    public void run() {
        while (true) {
            Result value;
            int key = ThreadLocalRandom.current().nextInt(1, 3 + 1);
            try {
                value = rmiCache.getValue(Integer.toString(key));
            } catch (RemoteException e) {
                e.printStackTrace();
                continue;
            }
            System.out.println(String.format("%s get value with key: %d -> %s", name, key, value.toString()));
        }
    }
}
