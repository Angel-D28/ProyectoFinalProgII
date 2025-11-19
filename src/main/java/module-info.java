module co.edu.uniquindio.poo.neodelivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires java.compiler;
    requires com.github.librepdf.openpdf;
    requires jakarta.mail;
    requires jdk.jshell;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires javafx.graphics;


    exports co.edu.uniquindio.poo.neodelivery.controllers;
    opens co.edu.uniquindio.poo.neodelivery.controllers to javafx.fxml;
    exports co.edu.uniquindio.poo.neodelivery.App;
    opens co.edu.uniquindio.poo.neodelivery.App to javafx.fxml;
    exports co.edu.uniquindio.poo.neodelivery.model;
    opens co.edu.uniquindio.poo.neodelivery.model to javafx.fxml;
}