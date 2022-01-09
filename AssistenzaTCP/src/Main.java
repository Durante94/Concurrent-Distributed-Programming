public class Main {
    public static void main(String[] args) {
        for(int i = 0; i<20; i++) {
            new Thread(new Client("localhost", 2456)).start();
        }
    }
}
