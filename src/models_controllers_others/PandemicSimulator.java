package models_controllers_others;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

/**
 *
 */
public class PandemicSimulator extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //fxml is the equivalent of HTML in javaFX
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../pandemic.fxml"));
        Parent root = loader.load(); //JavaFX is based on a graph like structure
        //Creating the Model for the controller(Model-View-Controller paradigm)
        new PandemicModel(loader.getController());

        primaryStage.setTitle("Pandemic Simulator");
        Scene scene = new Scene(root);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
        primaryStage.setScene(scene);


        //Handles JavaFX window close button
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.setResizable(false); //Fixed size, mainly due to the canvas
        primaryStage.show(); //Starting the application
    }


    public static void main(String[] args) {
        launch(args);
    }
}
