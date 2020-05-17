package com.devandrepascoa.main;

import com.devandrepascoa.data_structure.Person;
import com.devandrepascoa.fxgraph.cells.PersonCell;
import com.devandrepascoa.fxgraph.graph.Graph;
import com.devandrepascoa.fxgraph.graph.Model;
import com.devandrepascoa.fxgraph.layout.TreeLayout;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.abego.treelayout.Configuration;

/**
 * A controller for the graph.fxml view, the model however is implemented by the fx_graph graph visualizer
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class GraphController {
    //Object references from FXML file
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

    //Cache variables
    private Person selected_person;
    private Scene sim_scene;
    private Graph graph;


    /**
     * Creates the graph
     */
    public void create_graph() {
        this.graph = new Graph(this);
        addTreeComponents(graph);
        TreeLayout layout = new TreeLayout(300, 200, Configuration.Location.Bottom);
        layout.execute(graph);

        canvas_pane.getChildren().add(graph.getPane()); //Adds the graph canvas to the left Pane
    }


    /**
     * Adds data from the pandemic model to the graph
     *
     * @param graph the graph to add data
     */
    private void addTreeComponents(Graph graph) {
        final Model graph_model = graph.getModel();

        graph_model.fromPersonTree(selected_person);

        graph.endUpdate();
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

    //ACCESSORS

    public void setSim_scene(Scene sim_scene) {
        this.sim_scene = sim_scene;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     * Adjusts right pane visualization based on selected cell
     *
     * @param selected_person_cell currently selected cell(RED node)
     */
    public void setSelected_person(PersonCell selected_person_cell) {
        this.selected_person = selected_person_cell.getPerson();
        this.infected_people_lbl.setText("Infected People: " + selected_person.getInfectedPeople().size());
        this.in_hospital_lbl.setText("In Hospital: " + selected_person.isInHospital());
        this.state_lbl.setText(selected_person.getState().toString());
        this.name_lbl.setText("Name: " + selected_person.getName());
        this.time_since_infection_lbl.setText("TSI: " + selected_person.getTime_since_infected());
        String current_url = "/photo_ids/" + Utils.intToString(selected_person.getPhoto_id(), 4) + ".jpg";
        try {
            this.image_view.setImage(new Image(getClass().getResource(current_url).toURI().toString()));
        } catch (Exception e) {
            System.err.println("Incorrect URL, check file integrity");
        }
    }


}
