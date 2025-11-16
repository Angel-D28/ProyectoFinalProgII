module co.edu.uniquindio.poo.neodelivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires jdk.jshell;
    requires org.apache.pdfbox;
    requires java.desktop;


    exports co.edu.uniquindio.poo.neodelivery.controllers;
    exports co.edu.uniquindio.poo.neodelivery.model;
    exports co.edu.uniquindio.poo.neodelivery.model.utils;
    exports co.edu.uniquindio.poo.neodelivery.App;

    opens co.edu.uniquindio.poo.neodelivery.controllers to javafx.fxml;
    opens co.edu.uniquindio.poo.neodelivery.model to javafx.base;
    opens co.edu.uniquindio.poo.neodelivery.App to javafx.fxml;
}