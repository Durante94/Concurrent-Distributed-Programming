import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Author Fabrizio Durante
 * 11/01/2022 09:42
 */
public interface CacheHandler extends Remote {
    Result getValue(String id) throws RemoteException;
}
