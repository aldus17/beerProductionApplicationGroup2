package com.mycompany.presentation.breweryWorker;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BrewWorker extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/breweryWorker/BrewWorker_UI.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        root.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpt;", stage.widthProperty().divide(60)));

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
