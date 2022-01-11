import java.util.concurrent.ThreadLocalRandom;

public class PacketGenerator implements Runnable{
    private Core core;
    private int limit;

    public PacketGenerator(Core core, int limit) {

        this.core = core;
        this.limit = limit;
    }

    @Override
    public void run() {
        int randomNumberTime;
        int randomNumberRes;
        while(true)
        {
            randomNumberTime = ThreadLocalRandom.current().nextInt(1, 10 + 1);
            randomNumberRes = ThreadLocalRandom.current().nextInt(1, limit/2 + 1);
            this.core.serveMe(randomNumberRes, randomNumberTime);
            System.out.println("Sending request(" + randomNumberRes + "," + randomNumberTime + ")");
        }
    }
}
