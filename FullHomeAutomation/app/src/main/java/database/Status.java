package database;

/**
 * Created by Admin on 3/16/2016.
 */
public class Status {
    int id;
    String status;

    public Status() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Status(int id, String status) {
        this.id = id;
        this.status = status;
    }
}
