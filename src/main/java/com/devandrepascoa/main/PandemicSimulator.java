package com.devandrepascoa.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Main driver, starts the application
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 * TODO
 * -Add resizable window capability
 */
public class PandemicSimulator extends Application {

    //Cache variables for resource names
    private static ArrayList<String> first_names;
    private static ArrayList<String> last_names;

    /**
     * Initializes the simulator
     * @param window the window for which we will draw
     * @throws IOException thrown exception due to the loader.load() which accesses an external file
     */
    @Override
    public void start(Stage window) throws IOException {

        //Generates the cache names
        first_names = Utils.readAllFileIntoArray(getClass().getResource("/names_dataset/first_names.all.txt"));
        last_names = Utils.readAllFileIntoArray(getClass().getResource("/names_dataset/last_names.all.txt"));

        //fxml is the equivalent of HTML in javaFX
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pandemic.fxml"));
        Parent root = loader.load(); //JavaFX is based on a graph like structure

        //Creating the Model for the controller(Model-View-Controller paradigm)
        new PandemicModel(loader.getController());

        //Sets window settings
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

        window.setResizable(false); //Fixed size window, mainly due to the canvas
        window.show(); //Starting the window
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static ArrayList<String> getLast_names() {
        return last_names;
    }

    public static ArrayList<String> getFirst_names() {
        return first_names;
    }
}


