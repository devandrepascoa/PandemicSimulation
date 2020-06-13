package com.devandrepascoa.main;

import com.devandrepascoa.data_structure.Person;
import com.devandrepascoa.fxgraph.cells.PersonCell;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A controller for the pandemic.fxml file, used for
 * controlling the fxml "View", based on the
 * Model-View-Controller design pattern
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class PandemicController {
    //FXML object references
    @FXML
    private Spinner<Integer> death_prob_spin;
    @FXML
    private Button reset_btn;
    @FXML
    private Spinner<Integer> death_prob_hos_spin;
    @FXML
    private LineChart<String, Number> main_chart;
    @FXML
    private Button start_btn;
    @FXML
    private Slider infective_r_slider;
    @FXML
    private Label R0;
    @FXML
    private Spinner<Integer> population_spinner;
    @FXML
    private Spinner<Integer> hospital_cap_spinner;
    @FXML
    private Label num_beds_filled;
    @FXML
    private Canvas main_canvas;
    @FXML
    private Slider recovery_slider;
    @FXML
    private Slider symptoms_slider;

    //Cache variables, all initialized at standard values,
    //so no need for constructors
    private int recovery_amount;
    private int symptoms_amount;
    private int infective_r_amount;
    private int population_size;
    private int death_prob_hos;
    private int death_prob;
    private int hospital_cap;
    private boolean running;
    private boolean reset;
    private PandemicModel model;

    /**
     * Method that will run whenever FXML is loaded,
     * {@link FXML} anottation used for making method private,
     * so the {@link FXMLLoader} still has access to it,
     * parses json config and places defaults all over the program
     */
    @FXML
    private void initialize() throws IOException, URISyntaxException {
        Gson json_parser = new Gson();
        JsonObject config = json_parser.fromJson(Files.readString(Paths.get(getClass().getResource("/config.json").toURI())), JsonObject.class);
        //Setting constants based on config file
        Person.size = config.get("size").getAsInt();
        Person.death_prob = config.get("death_prob").getAsInt();
        Person.death_prob_hospital = config.get("death_prob_hospital").getAsInt();
        Person.infective_radius = config.get("infective_radius").getAsInt();
        Person.recovery_time = config.get("recovery_time").getAsInt();
        Person.symptoms_time = config.get("symptoms_time").getAsInt();
        System.out.println(config.toString());

        //Creates all the sliders for adjusting hyperparameters
        createSpinners(config.get("hospital_capacity").getAsInt(),
                config.get("num_people").getAsInt(), config.get("death_prob_hospital").getAsInt(),
                config.get("death_prob").getAsInt());

        //Creates all the spinners for adjusting hyperparameters
        createSliders(config.get("recovery_time").getAsInt(),
                config.get("symptoms_time").getAsInt(), config.get("infective_radius").getAsInt());

    }

    /**
     * Method that creates the spinner factories, and adds listeners
     * which will be used for updating the cache variables
     */
    private void createSpinners(int initial_hospital_capacity, int initial_num_people, int initial_death_prob_hospital, int initial_death_prob) {
        //Value factory for the spinners, it enables them(wouldn't work otherwise)
        SpinnerValueFactory<Integer> hos_spin_fac = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, initial_hospital_capacity);
        hospital_cap_spinner.setValueFactory(hos_spin_fac);
        SpinnerValueFactory<Integer> pop_spin_fac = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, initial_num_people);
        population_spinner.setValueFactory(pop_spin_fac);

        SpinnerValueFactory<Integer> death_prob_hos_spin_fac = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, initial_death_prob_hospital);
        death_prob_hos_spin.setValueFactory(death_prob_hos_spin_fac);
        SpinnerValueFactory<Integer> death_prob_spin_fac = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, initial_death_prob);
        death_prob_spin.setValueFactory(death_prob_spin_fac);

        //Presetting the cache variables
        this.hospital_cap = hos_spin_fac.getValue();
        this.population_size = pop_spin_fac.getValue();
        this.death_prob_hos = death_prob_hos_spin_fac.getValue();
        this.death_prob = death_prob_spin_fac.getValue();

        //Creating a listener to update cache variables
        hospital_cap_spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.hospital_cap = newValue;
        });
        population_spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.population_size = newValue;
        });
        death_prob_hos_spin.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.death_prob_hos = newValue;
        });
        death_prob_spin.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.death_prob = newValue;
        });
    }

    /**
     * method that adds listeners to the sliders
     * which will be used for updating the cache variables
     */
    private void createSliders(int initial_recovery_time, int initial_symptoms_time, int initial_infective_radius) {
        //Listens for a change in the sliders, and if there's one, update their respective amount
        recovery_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.recovery_amount = newValue.intValue();
            if (this.recovery_amount < symptoms_amount) {
                this.recovery_amount = symptoms_amount;
                this.recovery_slider.setValue(this.recovery_amount);
            }
        });
        recovery_slider.setMax(100000);
        recovery_slider.setMin(0);
        recovery_slider.setValue(initial_recovery_time); //Sets default value
        symptoms_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.symptoms_amount = newValue.intValue();
            if (this.symptoms_amount > this.recovery_amount) {
                this.symptoms_amount = this.recovery_amount;
                this.symptoms_slider.setValue(this.symptoms_amount);
            }
        });
        symptoms_slider.setMax(100000);
        symptoms_slider.setMin(0);
        symptoms_slider.setValue(initial_symptoms_time);
        infective_r_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.infective_r_amount = newValue.intValue();
        });
        infective_r_slider.setMax(50);
        infective_r_slider.setMin(1);
        infective_r_slider.setValue(initial_infective_radius);

        //Pre assigning values to cache variables
        this.recovery_amount = (int) recovery_slider.getValue();
        this.symptoms_amount = (int) symptoms_slider.getValue();
        this.infective_r_amount = (int) infective_r_slider.getValue();
    }


    /**
     * Method that will be called when start/stop button
     * is clicked, will update running cache variable
     */
    @FXML
    private void start_clicked(ActionEvent actionEvent) {
        if (this.running) {
            this.running = false;
            this.start_btn.setText("Start");
        } else {
            this.running = true;
            this.start_btn.setText("Stop");
        }
    }

    /**
     * Method used for switching between the Simulator mode and the Graph Visualization Mode
     */
    @FXML
    private void graph_btn_clicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/graph.fxml"));
        turnOff();
        Parent root = loader.load();

        //Saves Simulator mode scene in cache for returning to it
        Scene root_scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene previousScene = window.getScene();

        //Handles JavaFX window close button
        window.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        //Sets window settings
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(root_scene);
        window.setResizable(false); //Fixed size, mainly due to the canvas
        window.setScene(root_scene);
        window.show();

        //Retrieves the graph controller and presets some variables
        GraphController graph_controller = loader.getController();
        graph_controller.setSim_scene(previousScene);
        graph_controller.setSelected_person(new PersonCell(model.getCity().getZero_day()));

        graph_controller.create_graph();
    }

    /**
     * Turns off the model
     */
    public void turnOff() {
        if (isRunning()) {
            start_clicked(null);
        }
    }


    /**
     * Method that is called when reset is clicked,
     * updates respective cache variable
     */
    @FXML
    private void reset_clicked(ActionEvent actionEvent) {
        this.reset = true;
    }

    //ACCESSORS
    public Canvas getCanvas() {
        return main_canvas;
    }

    public LineChart<String, Number> getChart() {
        return this.main_chart;
    }

    public boolean isReset() {
        return reset;
    }

    public boolean isRunning() {
        return running;
    }

    public void turnResetOff() {
        this.reset = false;
    }

    public void setR0(double r0) {
        this.R0.setText("R0: " + r0);
    }

    public void setNum_beds_filled(int num_beds) {
        this.num_beds_filled.setText("Beds Filled: " + num_beds);
    }

    public void setModel(PandemicModel pandemicModel) {
        this.model = pandemicModel;
    }

    public int getRecovery_amount() {
        return recovery_amount;
    }

    public int getSymptoms_amount() {
        return symptoms_amount;
    }

    public int getInfective_r_amount() {
        return infective_r_amount;
    }

    public int getPopulation_size() {
        return population_size;
    }

    public int getDeath_prob_hos() {
        return death_prob_hos;
    }

    public int getDeath_prob() {
        return death_prob;
    }

    public int getHospital_cap() {
        return hospital_cap;
    }
}
