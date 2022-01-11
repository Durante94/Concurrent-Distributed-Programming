public class Request {

    private int resources;
    private int seconds;
    private boolean isValid;

    public Request(int resources, int seconds) {
        this.resources = resources;
        this.seconds = seconds;
        this.isValid = false;
    }

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid() {
        isValid = true;
    }

    @Override
    public String toString() {
        return "Request{" +
                "resources=" + resources +
                ", seconds=" + seconds +
                '}';
    }
}
