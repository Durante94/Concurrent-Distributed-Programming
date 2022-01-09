import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Author Fabrizio Durante
 * 09/01/2022 12:10
 */
public interface Server extends Remote {

    File readFile(String id) throws RemoteException;

    File accessFile(String id, String clientId) throws RemoteException;

    String writeFile(File file, String clientId) throws RemoteException;
}
