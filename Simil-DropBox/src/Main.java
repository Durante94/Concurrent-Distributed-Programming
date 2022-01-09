import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Author Fabrizio Durante
 * 09/01/2022 15:31
 */
public class Main {
    public static void main(String[] args) {
        Server s = null;
        try {
            s = (Server) Naming.lookup("server");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            e.printStackTrace();
            return;
        }

        Client a = new Client("a", s),
                b = new Client("b", s),
                c = new Client("c", s);

        new Thread(a).start();
        new Thread(b).start();
        new Thread(c).start();
    }
}
