package co.edu.univalle.poe.mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("21 Blackjack");
        stage.setScene(scene);
        stage.setOnCloseRequest(evento -> {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que quieres cerrar el juego?");
            if (alerta.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
                evento.consume();
            }
        });

        stage.show();
    }
}
