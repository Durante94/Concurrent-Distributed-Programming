import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Runnable{
    private Socket s;
    private Boolean isValid;
    private ObjectOutputStream oos;

    public Client(String host, int port) {
        try {
            this.s = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
            isValid = false;
        }
    }

    @Override
    public void run() {
        if(!isValid){
            return;
        }

        try {
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(new Request(Thread.currentThread().getName(), "666"));
            System.out.println(Thread.currentThread().getName() + " submitted new request");
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
