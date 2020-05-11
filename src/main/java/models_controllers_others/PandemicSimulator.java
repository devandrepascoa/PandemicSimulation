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
 * Main driver, starts the application
 * @author André Páscoa, André Carvalho
 * @version 2.5.0
 */
public class PandemicSimulator extends Application {

    /**
     * @param window the window for which we will draw
     * @throws Exception thrown exception due to the loader.load() which accesses an external file
     */
    @Override
    public void start(Stage window) throws Exception {
        //fxml is the equivalent of HTML in javaFX
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pandemic.fxml"));
        Parent root = loader.load(); //JavaFX is based on a graph like structure
        //Creating the Model for the controller(Model-View-Controller paradigm)
        new PandemicModel(loader.getController());

        window.setTitle("Pandemic Simulator");
        Scene scene = new Scene(root);
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(scene);
        window.setScene(scene);


        //Handles JavaFX window close button
        window.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        window.setResizable(false); //Fixed size, mainly due to the canvas
        window.show(); //Starting the application
    }


    public static void main(String[] args) {
        launch(args);
    }
}
