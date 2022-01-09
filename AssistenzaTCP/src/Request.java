import java.io.Serializable;

public class Request implements Serializable {
    private String problem;
    private String phone;
    private String id;

    public Request(String problem, String phone) {
        this.problem = problem;
        this.phone = phone;
        this.id = "";
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Request{" +
                "problem='" + problem + '\'' +
                ", phone='" + phone + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
