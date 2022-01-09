import java.rmi.RemoteException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Author Fabrizio Durante
 * 09/01/2022 15:30
 */
public class Client implements Runnable {
    private String id;
    private Server rmi;

    public Client(String id, Server rmi) {
        this.id = id;
        this.rmi = rmi;
    }

    @Override
    public void run() {
        while (true) {
            if (ThreadLocalRandom.current().nextInt(1, 2 + 1) == 1) {
                File read = null;

                try {
                    read = rmi.readFile(Integer.toString(ThreadLocalRandom.current().nextInt(1, 3 + 1)));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + ", read file: " + read);
            } else
                writeFileRMI();
        }
    }

    private void writeFileRMI() {
        File download = null;
        try {
            download = rmi.accessFile(Integer.toString(ThreadLocalRandom.current().nextInt(1, 3 + 1)), id);
        } catch (RemoteException e) {
            e.printStackTrace();
            return;
        }

        if (download == null) {
            System.out.println("Invalid client id");
            return;
        }

        System.out.println(Thread.currentThread().getName() + ", downloaded file: " + download);

        download.setPayload(download.getPayload()
                + Thread.currentThread().getName() + ", modificato a caso\r\n");

        String status = "";
        try {
            status = rmi.writeFile(download, id);
        } catch (RemoteException e) {
            e.printStackTrace();
            return;
        }

        System.out.println(Thread.currentThread().getName() + " uploaded file: " + download + "\r\nStatus: " + status);
    }
}
