import java.util.Comparator;
import java.util.LinkedList;

public class Core implements Runnable{

    private LinkedList<Request> requests;
    private int M;
    private IntHolder N;

    public Core(int M, int N) {
        this.requests = new LinkedList<>();
        this.M = M;
        this.N = new IntHolder(N);
    }

    public void serveMe(int resources, int seconds){
        Request r = new Request(resources, seconds);
        synchronized (requests){
            requests.add(r);
            System.out.println("Request added: " + r);
            requests.sort(Comparator.comparingInt(Request::getResources));
            System.out.println(requests);
            requests.notify();
        }

        synchronized (r){
            while (!r.isValid()){
                try {
                    r.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("ServeMe of " + r + " completed");
        }
    }

    @Override
    public void run() {

        for (int i=0;i<M;i++){
            new Thread(new Executor(requests, N)).start();
        }

    }
}
