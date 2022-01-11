public class Main {
    public static void main(String[] args) {

        int limit = 26;
        Core c = new Core(2,limit);
        new Thread(c).start();
        for (int i=0;i<6;i++){
            new Thread(new PacketGenerator(c,limit)).start();
        }
    }
}
