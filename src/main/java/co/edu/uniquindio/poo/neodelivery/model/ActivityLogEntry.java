package co.edu.uniquindio.poo.neodelivery.model;

public class ActivityLogEntry {
    private String date;
    private String user;
    private String action;

    public ActivityLogEntry(String date, String user, String action) {
        this.date = date;
        this.user = user;
        this.action = action;
    }

    public String getDate() { return date; }
    public String getUser() { return user; }
    public String getAction() { return action; }
}
