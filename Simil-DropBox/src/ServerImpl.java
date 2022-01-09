import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Author Fabrizio Durante
 * 09/01/2022 12:11
 */
public class ServerImpl extends UnicastRemoteObject implements Server {
    private Map<String, File> files;

    public static void main(String[] args) {
        try {
            ServerImpl serverRmi = new ServerImpl();
            Naming.rebind("server", serverRmi);
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    protected ServerImpl() throws RemoteException {
        super();
        files = new HashMap<>();
    }

    @Override
    public File readFile(String id) throws RemoteException {
        synchronized (files) {
            return files.get(id);
        }
    }

    @Override
    public File accessFile(String id, String clientId) throws RemoteException {
        File toReturn;

        if (clientId == null || clientId.isEmpty()) return null;

        synchronized (files) {
            toReturn = files.get(id);
            if (toReturn == null) {
                toReturn = new File(id, "");
                files.put(id, toReturn);

                toReturn.setIdWriter(clientId);

                return toReturn;
            }
        }

        synchronized (toReturn) {
            while (toReturn.isLocked()) {
                try {
                    toReturn.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            toReturn.setIdWriter(clientId);
            return toReturn;
        }
    }

    @Override
    public String writeFile(File file, String clientId) throws RemoteException {
        if (file == null) return "File not found";

        File toUpdate;
        synchronized (files) {
            if (!files.containsKey(file.getId())) return "File not found";

            toUpdate = files.get(file.getId());
        }
        synchronized (toUpdate) {
            if (toUpdate.getIdWriter().compareTo(file.getIdWriter()) != 0 ||
                    clientId.compareTo(toUpdate.getIdWriter()) != 0)
                return "Access Denied";

            toUpdate.setPayload(file.getPayload());
            toUpdate.setIdWriter("");
            toUpdate.notify();
            return "Success";
        }
    }
}
