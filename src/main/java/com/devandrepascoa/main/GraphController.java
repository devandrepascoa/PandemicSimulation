package com.devandrepascoa.main;

import com.devandrepascoa.fxgraph.graph.Graph;
import com.devandrepascoa.fxgraph.graph.Model;
import com.devandrepascoa.fxgraph.layout.AbegoTreeLayout;
import com.devandrepascoa.data_structure.Person;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.abego.treelayout.Configuration;

/**
 * A controller for the graph.fxml view, the model however is implemented by the com.fxgraph library,
 * which was modified
 * @author André Páscoa, André Carvalho
 * @version 2.5.0
 */
public class GraphController {
    @FXML
    private Label in_hospital_lbl;
    @FXML
    private Label state_lbl;
    @FXML
    private Label time_since_infection_lbl;
    @FXML
    private Label infected_people_lbl;
    @FXML
    private ImageView image_view;
    @FXML
    private Label name_lbl;
    @FXML
    private Pane canvas_pane;

    private Person selected_person;
    private Scene sim_scene;
    private Graph graph;


    /**
     * Creates the graph and presets the labels and image
     */
    public void create_graph() {
        Graph tree = new Graph();
        addTreeComponents(tree);
        canvas_pane.getChildren().add(tree.getCanvas()); //Adds the graph canvas to the left Pane
        name_lbl.setText("Name: " + selected_person.getName());
        infected_people_lbl.setText("Infected People: " + selected_person.getInfectedPeople().size());
        time_since_infection_lbl.setText("TSI: " + selected_person.getTime_since_infected());
        state_lbl.setText(selected_person.getState().toString());
        in_hospital_lbl.setText("In Hospital: " + selected_person.isInHospital());
    }


    /**
     * Adds data from the pandemic model to the graph
     * @param graph the graph to add data
     */
    private void addTreeComponents(Graph graph) {
        final Model graph_model = graph.getModel();
        graph.beginUpdate();

        graph_model.fromPersonTree(selected_person);

        graph.endUpdate();
        graph.layout(new AbegoTreeLayout(200, 200, Configuration.Location.Top));
    }

    /**
     * Goes back to the simulation scene
     */
    public void go_to_sim(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();


        //Handles JavaFX window close button
        window.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
        window.setResizable(false); //Fixed size, mainly due to the canvas
        window.setScene(sim_scene);
        window.show();

    }

    public void setSim_scene(Scene sim_scene) {
        this.sim_scene = sim_scene;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public void setSelected_person(Person selected_person) {
        this.selected_person = selected_person;
    }
}
