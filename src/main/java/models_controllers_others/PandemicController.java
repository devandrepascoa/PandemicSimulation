package models_controllers_others;

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

/**
 * A controller for the pandemic.fxml file, used for
 * controlling the fxml "View", based on the
 * Model-View-Controller design pattern
 *
 * @author André Páscoa, André Carvalho
 * @version 2.5.0
 */
public class PandemicController {
    @FXML
    private LineChart<String, Number> main_chart;
    @FXML
    private Button start_btn;
    @FXML
    private Slider infective_r_slider;
    @FXML
    private Button reset_btn;
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

    //Cache variables
    private int recovery_amount;
    private int symptoms_amount;
    private int infective_r_amount;
    private int population_size;
    private int hospital_cap;
    private boolean running;
    private boolean reset;
    private PandemicModel model;

    public PandemicController() {
        recovery_amount = 0;
        symptoms_amount = 0;
        infective_r_amount = 0;
        running = false;
        reset = false;
    }

    /**
     * method is called after loading the FXML file, which does not happen in the constructor
     * so {@link FXML} variables will be loaded.
     */
    @FXML
    private void initialize() {
        //Pre assigning values to cache variables
        this.recovery_amount = (int) recovery_slider.getValue();
        this.symptoms_amount = (int) symptoms_slider.getValue();
        this.infective_r_amount = (int) infective_r_slider.getValue();

        //Listens for a change in the sliders, and if there's one, update their respective amount
        recovery_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.recovery_amount = newValue.intValue();
            if (this.recovery_amount < symptoms_amount) {
                this.recovery_amount = symptoms_amount;
                this.recovery_slider.setValue(this.recovery_amount);
            }
        });
        symptoms_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.symptoms_amount = newValue.intValue();
            if (this.symptoms_amount > recovery_amount) {
                this.symptoms_amount = recovery_amount;
                this.symptoms_slider.setValue(this.symptoms_amount);
            }
        });
        infective_r_slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.infective_r_amount = newValue.intValue();
        });

        //Value factory for the spinner, it enables the spinners(wouldn't work otherwise)
        SpinnerValueFactory<Integer> hos_spin_fac = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 50);
        hospital_cap_spinner.setValueFactory(hos_spin_fac);
        SpinnerValueFactory<Integer> pop_spin_fac = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1000);
        population_spinner.setValueFactory(pop_spin_fac);

        //Presetting the cache variables
        hospital_cap = hos_spin_fac.getValue();
        population_size = pop_spin_fac.getValue();

        //Creating a listener to update cache variables
        hospital_cap_spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.hospital_cap = newValue;
        });
        population_spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.population_size = newValue;
        });
    }


    /**
     * Happens when start button is clicked
     */
    @FXML
    private void start_clicked(ActionEvent actionEvent) {
        if (!this.running) {
            this.running = true;
            this.start_btn.setText("Stop");
        } else {
            this.running = false;
            this.start_btn.setText("Start");
        }
    }

    /**
     * Used for switching between the Simulator mode and the Graph Visualization Mode
     */
    @FXML
    private void graph_btn_clicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/graph.fxml"));

        Parent root = loader.load(); //JavaFX is based on a graph like structure

        Scene root_scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene previousScene = window.getScene();

        //Handles JavaFX window close button
        window.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(root_scene);
        window.setResizable(false); //Fixed size, mainly due to the canvas
        window.setScene(root_scene);
        window.show();

        //Retrieves the graph controller and presets some variables
        GraphController graph_controller = loader.getController();
        graph_controller.setSim_scene(previousScene);
        graph_controller.setSelected_person(model.getCity().getZero_day());

        graph_controller.create_graph();
    }



    @FXML
    private void reset_clicked(ActionEvent actionEvent) {
        this.reset = true;
    }

    public Canvas getCanvas() {
        return main_canvas;
    }

    public LineChart<String, Number> getChart() {
        return this.main_chart;
    }

    public int getRecovery_amount() {
        return recovery_amount;
    }

    public int getSymptoms_amount() {
        return symptoms_amount;
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

    public int getInfective_r_amount() {
        return infective_r_amount;
    }

    public void setR(double R) {
//        this.R.setText("R: " + R);
    }

    public void setR0(double r0) {
        this.R0.setText("R0: " + r0);
    }

    public void setNum_beds_filled(int num_beds) {
        this.num_beds_filled.setText("Beds Filled: " + num_beds);
    }

    public int getHospital_cap() {
        return hospital_cap;
    }

    public int getPopulation_size() {
        return population_size;
    }

    public void setModel(PandemicModel pandemicModel) {
        this.model = pandemicModel;
    }
}
