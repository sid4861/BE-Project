package database;

/**
 * Created by abc on 21/11/16.
 */

public class History {
    int id;
    String lampname;
    String time;
    String status;

    public History() {
    }

    public History(int id, String lampname, String time, String status) {
        this.id = id;
        this.lampname = lampname;
        this.time = time;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLampname() {
        return lampname;
    }

    public void setLampname(String lampname) {
        this.lampname = lampname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
