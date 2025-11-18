package co.edu.uniquindio.poo.neodelivery.App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.application.HostServices;
import java.io.IOException;

public class HelloApplication extends Application {

    private static HostServices hostServices;

    @Override
    public void start(Stage stage) throws IOException {
        hostServices = getHostServices(); // Guardamos referencia al host services

        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource("/co/edu/uniquindio/poo/neodelivery/loginView.fxml"));
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