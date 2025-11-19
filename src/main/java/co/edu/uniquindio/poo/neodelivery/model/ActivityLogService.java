package co.edu.uniquindio.poo.neodelivery.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActivityLogService {

    private static ObservableList<ActivityLogEntry> logList = FXCollections.observableArrayList();

    public static void log(String user, String action) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = LocalDateTime.now().format(formatter);
        logList.add(new ActivityLogEntry(date, user, action));
    }

    public static ObservableList<ActivityLogEntry> getLogs() {
        return logList;
    }
}
