import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CacheHandler extends Remote {
    Result getValue(String id) throws RemoteException;

    void invalidateValue(String id) throws RemoteException;
}
