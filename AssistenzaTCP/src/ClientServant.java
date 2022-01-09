import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class ClientServant implements Runnable {
    private int port;
    private String host;
    private LinkedList<Request> requests;
    private ServerSocket ss;


    public ClientServant(LinkedList<Request> requests, int port, String host) {
        this.requests = requests;
        this.port = port;
        this.host = host;
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket();
            InetSocketAddress isa = new InetSocketAddress(host, port);
            ss.bind(isa);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            Socket s = null;
            try {
                s = ss.accept();
                (new Thread(new Servant(s, requests))).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class Servant implements Runnable {
    private Socket s;
    LinkedList<Request> requests = null;

    Servant(Socket s, LinkedList<Request> requests) {
        this.requests = requests;
        this.s = s;
    }

    public void run() {
        Request r = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(s.getInputStream());
            r = (Request)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        synchronized (requests) {
            r.setId(Integer.toString(ThreadLocalRandom.current().nextInt()));
            requests.add(r);
        }
    }
}
