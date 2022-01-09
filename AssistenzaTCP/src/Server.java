import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server implements Runnable{
    private LinkedList<Request> requests;
    private ServerSocket ss;   //dai client
    private String host;
    private int k;
    private int j;
    private int m;

    public Server(int k, int j, int m, String host) {
        this.k = k;
        this.j = j;
        this.m = m;
        this.host = host;
        requests = new LinkedList<>();
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        new Thread(new Server(2456, 2457, 2458, "localhost")).start();
    }
}
