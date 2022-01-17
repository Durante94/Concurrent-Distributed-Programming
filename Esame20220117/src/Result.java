import java.io.Serializable;

public class Result implements Serializable {
    private boolean valid;

    public Result() {
        this.valid = false;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "Result{" +
                "valid=" + valid +
                '}';
    }
}
