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
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.annotation;

    exports co.edu.uniquindio.poo.neodelivery.controllers;
    opens co.edu.uniquindio.poo.neodelivery.controllers to javafx.fxml;

    exports co.edu.uniquindio.poo.neodelivery.model.Repository;

    opens co.edu.uniquindio.poo.neodelivery.model to javafx.fxml, javafx.base, com.fasterxml.jackson.databind;
    opens co.edu.uniquindio.poo.neodelivery.model.State to com.fasterxml.jackson.databind;

    exports co.edu.uniquindio.poo.neodelivery.App;
    opens co.edu.uniquindio.poo.neodelivery.App to javafx.fxml;

}