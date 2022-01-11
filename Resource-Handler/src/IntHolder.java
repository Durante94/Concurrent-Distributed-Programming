public class IntHolder {

    private int holder;

    public IntHolder(int holder) {
        this.holder = holder;
    }

    public int getHolder() {
        return holder;
    }

    public void setHolder(int holder) {
        this.holder = holder;
    }

    public int getValue(){
        return this.holder;
    }

    @Override
    public String toString() {
        return "IntHolder{" +
                "holder=" + holder +
                '}';
    }
}
