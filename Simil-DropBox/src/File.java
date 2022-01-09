import java.io.Serializable;

/**
 * Author Fabrizio Durante
 * 09/01/2022 12:13
 */
public class File implements Serializable {

    private String id;
    private String payload;
    private String idWriter;

    public File(String id, String payload) {
        this.id = id;
        this.payload = payload;
        idWriter = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public boolean isLocked() {
        return !idWriter.isEmpty();
    }

    public String getIdWriter() {
        return idWriter;
    }

    public void setIdWriter(String idWriter) {
        this.idWriter = idWriter;
    }

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", payload='" + payload + '\'' +
                ", idWriter='" + idWriter + '\'' +
                '}';
    }
}
