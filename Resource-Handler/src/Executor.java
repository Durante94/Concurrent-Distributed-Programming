import java.util.LinkedList;

public class Executor implements Runnable{

    private LinkedList<Request> requests;
    private IntHolder resources;

    public Executor(LinkedList<Request> requests, IntHolder resources) {
        this.requests = requests;
        this.resources = resources;
    }

    @Override
    public void run() {

        Request r;
        while (true){
            synchronized (requests){
                while (requests.isEmpty()){
                    try {
                        requests.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (resources){
                    while (true) {
                        r = requests.getFirst();
                        if(r.getResources() < resources.getValue())
                            break;
                        try {
                            resources.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    resources.setHolder(resources.getValue() - r.getResources());
                }
                requests.remove();
                System.out.println("Started processing: " + r);
            }
            try {
                Thread.sleep(r.getSeconds()*1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (r){
                r.setValid();
                r.notify();
                System.out.println("Finished processing " + r);
                synchronized (resources){
                    resources.setHolder(resources.getValue() + r.getResources());
                    System.out.println("Available resources: " + resources.getValue());
                    resources.notify();
                }
            }
        }
    }
}
