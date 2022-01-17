import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ThreadLocalRandom;

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
            cacheHandler = (CacheHandler) Naming.lookup("serverCache");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
            return;
        }

        new Thread(new Client("A", cacheHandler)).start();
        new Thread(new Client("B", cacheHandler)).start();
        new Thread(new Client("C", cacheHandler)).start();
    }

    @Override
    public void run() {
        int numReq = 1, frequency=ThreadLocalRandom.current().nextInt(4, 10 + 1);
        while (true) {
            Result value;
            int key = ThreadLocalRandom.current().nextInt(1, 5 + 1);
            try {
                if (numReq % frequency == 0) {
                    rmiCache.invalidateValue(Integer.toString(key));
                    System.out.println(String.format("%s invalidate value for %d", name, key));
                } else {
                    value = rmiCache.getValue(Integer.toString(key));
                    System.out.println(String.format("%s get value with key: %d -> %s", name, key, value.toString()));
                }
            } catch (RemoteException e) {
                e.printStackTrace();
                continue;
            }

            numReq++;
        }
    }
}
