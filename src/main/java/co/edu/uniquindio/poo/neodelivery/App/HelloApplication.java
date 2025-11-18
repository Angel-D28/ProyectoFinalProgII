package co.edu.uniquindio.poo.neodelivery.App;

import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageAdmin;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageReports;
import co.edu.uniquindio.poo.neodelivery.model.*;
import co.edu.uniquindio.poo.neodelivery.model.Decorators.*;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManagePayments;
import co.edu.uniquindio.poo.neodelivery.model.gestores.ManageShipments;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/co/edu/uniquindio/poo/neodelivery/loginView.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("/images/logoCamionReportes.png"));
        stage.getIcons().add(icon);
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("NeoDelivery - Login");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}