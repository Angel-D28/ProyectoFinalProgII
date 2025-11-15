module co.edu.uniquindio.poo.neodelivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires jdk.jshell;

    exports co.edu.uniquindio.poo.neodelivery.controllers;
    opens co.edu.uniquindio.poo.neodelivery.controllers to javafx.fxml;

    exports co.edu.uniquindio.poo.neodelivery.App;
    opens co.edu.uniquindio.poo.neodelivery.App to javafx.fxml;
    opens co.edu.uniquindio.poo.neodelivery.model to javafx.base;
}