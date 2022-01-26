import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class CacheHandlerImpl extends UnicastRemoteObject implements CacheHandler {

    private final Map<String, Result> cache;

    public static void main(String[] args) {
        try {
            CacheHandlerImpl rmiCache = new CacheHandlerImpl();
            Naming.rebind("serverCache", rmiCache);
            System.out.println("Server has started");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public CacheHandlerImpl() throws RemoteException {
        super();
        cache = new HashMap<>();
    }

    @Override
    public Result getValue(String id) throws RemoteException {
        Result toReturn;

        //Trying to get the lock for the shared Map
        synchronized (cache) {
            System.out.println("Request for " + id);
            //Get or Set & Get the requested Result in the shared Map
            if (cache.containsKey(id)) {
                toReturn = cache.get(id);
            } else {
                toReturn = new Result();
                cache.put(id, toReturn);
                System.out.println("Added new " + toReturn + " for " + id);
            }
        }

        //Trying to get the lock for a possible concurrent Result
        synchronized (toReturn) {
            //If the result has not been calculated yet
            if (!toReturn.isValid()) {
                System.out.println("Processing " + toReturn + " for " + id);
                //Doing the calculations
                dummy(toReturn);
                System.out.println("Finished processing " + toReturn + " for " + id);
            }
        }
        System.out.println("Return request " + toReturn + " for " + id);
        return toReturn;
    }

    private void dummy(Result toExecute) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10 + 1) * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        toExecute.setValid(true);
    }

    @Override
    public void invalidateValue(String id) throws RemoteException {
        //Trying to get the lock for the shared Map
        synchronized (cache) {
            //Remove the object associated with the id
            cache.remove(id);
            System.out.println("Removed value for " + id);
        }
    }
}
