package models_controllers_others;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

/**
 * A controller for the pandemic.fxml file, used for
 * controlling the fxml "View", based on the
 * Model-View-Controller design pattern
 */
public class PandemicController {
    @FXML
    public LineChart<String, Number> main_chart;
    @FXML
    public Button start_btn;
    @FXML
    public Slider infective_r_slider;
    @FXML
    public Button reset_btn;
    @FXML
    private Canvas main_canvas;
    @FXML
    private Slider recovery_slider;
    @FXML
    private Slider symptoms_slider;


    private int recovery_amount;
    private int symptoms_amount;
    private boolean running;
    private boolean reset;
    private int infective_r_amount;

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
        infective_r_slider.valueProperty().addListener((observable, oldValue, newValue) -> this.infective_r_amount = newValue.intValue());

    }

    @FXML
    private void mouseClicked(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
    }

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

    public void reset_clicked(ActionEvent actionEvent) {
        this.reset = true;
    }

    public int getInfective_r_amount() {
        return infective_r_amount;
    }
}
