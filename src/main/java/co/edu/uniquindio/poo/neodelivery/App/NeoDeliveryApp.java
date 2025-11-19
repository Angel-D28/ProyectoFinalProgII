package co.edu.uniquindio.poo.neodelivery.App;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class NeoDeliveryApp extends Application {

    private static HostServices hostServices;

    @Override
    public void start(Stage stage) throws IOException {
        hostServices = getHostServices();
        FXMLLoader fxmlLoader = new FXMLLoader(NeoDeliveryApp.class.getResource("/co/edu/uniquindio/poo/neodelivery/loginView.fxml"));
        Image icon = new Image(getClass().getResourceAsStream("/images/logoCamionReportes.png"));
        stage.getIcons().add(icon);
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("NeoDelivery - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static HostServices getAppHostServices() {
        return hostServices;
    }

    public static void main(String[] args) {
        launch();
    }
}