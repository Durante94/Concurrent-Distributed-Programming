import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Author Fabrizio Durante
 * 11/01/2022 09:47
 */
public class CacheHandlerImpl extends UnicastRemoteObject implements CacheHandler {

    private final Map<String, Result> cache;

    public static void main(String[] args) {
        try {
            CacheHandlerImpl rmiCache = new CacheHandlerImpl();
            //System.setProperty("java.rmi.server.server", "192.168.1.107");
            Naming.rebind("server", rmiCache);
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected CacheHandlerImpl() throws RemoteException {
        super();
        cache = new HashMap<>();
    }

    @Override
    public Result getValue(String id) throws RemoteException {
        Result toReturn;
        synchronized (cache) {
            if (cache.containsKey(id)) {
                toReturn = cache.get(id);
            } else {
                toReturn = new Result();
                cache.put(id, toReturn);
            }
        }

        synchronized (toReturn) {
            if (!toReturn.isValid())
                dummy(toReturn);
        }

        return toReturn;
    }

    private void dummy(Result toExecute) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10 + 1));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        toExecute.setValid(true);
    }
}
