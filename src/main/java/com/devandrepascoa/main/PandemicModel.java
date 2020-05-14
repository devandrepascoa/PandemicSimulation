package com.devandrepascoa.main;

import com.devandrepascoa.data_structure.City;
import com.devandrepascoa.data_structure.Constants;
import com.devandrepascoa.data_structure.Person;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class will provide the canvas for the animation
 *
 * @author André Páscoa, André Carvalho
 * @version 2.5.0
 */
public class PandemicModel {

    public static int DELAY; //Update delay(Will update at 60 FPS)
    private long start_time; //Used for keeping track of time, useful for charting
    private City city;
    private final Constants constants;
    private int counter_paint;
    private int counter_update;

    /**
     * Chart data has to be of type {@link XYChart.Series} due to JavaFX charts
     * requiring an {@link javafx.collections.ObservableList}, which is a type of List which keeps track
     * of updates, this is used for easily updating the data for the chart
     */
    private final XYChart.Series<String, Number> chartNumInfected;
    private final XYChart.Series<String, Number> chartNumDead;
    private final PandemicController controller; //Holds the controller for manipulating the View (GUI)
    private int model_time;

    /**
     * Constructs the pandemic Model object
     */
    public PandemicModel(PandemicController controller) {
        //Initializations
        DELAY = 16;
        counter_paint = 0;
        model_time = 0;
        counter_update = 0;
        this.controller = controller;
        controller.setModel(this);
        constants = new Constants();
        fillConstants();
        city = new City(constants);
        start_time = System.currentTimeMillis();
        chartNumInfected = new XYChart.Series<>();
        chartNumDead = new XYChart.Series<>();
        chartNumInfected.setName("Infected");
        chartNumDead.setName("Deaths");
        controller.getChart().getData().add(chartNumDead);
        controller.getChart().getData().add(chartNumInfected);

        //Workload will be separated between two threads, one for GUI and another for the simulation

        //Worker timer, used for updating data structure
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (controller.isReset()) { //If the controller has told the model to reset
                    Platform.runLater(() -> { //Has to be on runLater because it update the UI
                        chartNumDead.getData().clear();
                        chartNumInfected.getData().clear();
                    });
                    model_time = 0;
                    fillConstants();
                    city = new City(constants);
                    counter_update = 0;
                    counter_paint = 0;
                    controller.turnResetOff();
                }
                if (controller.isRunning()) { //If it's running
                    update();

                    if (counter_update % 10 == 0)
                        update_chart();
                    counter_update++;
                    model_time += 16;
                }
            }
        }, 0, 16);

        //AnimationTimer used for keeping canvas at 60 Frame per second,
        // can't use swing Timer for updating UI due to JavaFX not liking
        // it very much
        AnimationTimer timer2 = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //Run later required when updating the UI
                Platform.runLater(() -> {
                    paint(controller.getCanvas());

                    counter_paint++;
                });

            }
        };
        timer2.start();
    }

    private void update_chart() {
        double current_time = model_time / 1000.0;
        //Since this operations involve UI, it requires the run Later method
        Platform.runLater(() -> {
            chartNumInfected.getData().add(new XYChart.Data<>(String.valueOf(current_time), city.getNum_infected()));
            chartNumDead.getData().add(new XYChart.Data<>(String.valueOf(current_time), city.getNum_dead()));
        });

    }

    /**
     * Fills the {@link Constants} instance with data from the {@link PandemicController}
     */
    private void fillConstants() {
        constants.width = (int) controller.getCanvas().getWidth();
        constants.height = (int) controller.getCanvas().getHeight();
        constants.infective_radius = controller.getInfective_r_amount();
        constants.symptoms_time = controller.getSymptoms_amount();
        constants.recovery_time = controller.getRecovery_amount();
        constants.num_people = controller.getPopulation_size();
        constants.hospital_capacity = controller.getHospital_cap();
        System.out.println(constants);
    }


    /**
     * Function that will be called every tick per delay time,
     * it will be used for painting the canvas based on the data
     * from a {@link City} instance
     *
     * @param canvas A kind of panel that will be used for painting
     */

    private void paint(Canvas canvas) {
        //Graphics context, used for painting the screen
        GraphicsContext g = canvas.getGraphicsContext2D();

        //Clearing screen before painting
        g.setFill(Color.WHITE); //Sets background color
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Painting the city
        city.paint(g);
    }


    /**
     * Method that updates all the variables from the {@link City} instance
     * data structure.
     */
    private void update() {

        //Updates the population
        double R = 0;
        for (int i = 0; i < city.getNumPeople(); i++) {
            Person selected_person = city.getPopulation().get(i);
            selected_person.update(city);
            if (selected_person.getState() != Person.State.SUSCEPTIBLE) {
                R += selected_person.getInfectedPeople().size();
            }
        }

        //Has to be here due to Threading and updating the UI
        final double R0 = MathUtils.getRoundedNumber((city.getZero_day().getInfectedPeople().size()), 2);
        final double finalR = MathUtils.getRoundedNumber(R / city.getNum_infected(), 2);
        System.out.println(finalR);
        Platform.runLater(() -> { //Communicates with the controller to update the UI
            controller.setR0(R0);
            controller.setR(finalR);
            controller.setNum_beds_filled(city.getHospital().getNum_filled_beds());
        });

        //Graph traversal test
//        if (counter_update % 100 == 0) {
//            LinkedList<Person> lista_nodes = city.iterativePreOrder();
//            for (Person p : lista_nodes) {
//                System.out.print(p.toString() + ",");
//            }
//
//            System.out.println("\nN_Inf_Calc: " + lista_nodes.size() + ", N_Inf_Reais: " + city.getNum_infected());
//        }
    }

    public City getCity() {
        return city;
    }
}