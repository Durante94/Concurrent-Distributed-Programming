/**
 * Author Fabrizio Durante
 * 11/01/2022 09:38
 */
public class Result {
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
