package models_controllers_others;

import data_structure.City;
import data_structure.Constants;
import data_structure.Person;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class will provide the canvas for the animation
 *
 * @author André Páscoa, André Carvalho
 * TODO
 * -Faster animations
 */
public class PandemicModel {

    public static int DELAY; //Update delay(Will update at 60 FPS)
    private long start_time; //Used for keeping track of time, useful for charting
    private City city;
    private Constants constants;
    private int counter_paint;
    private int counter_update;
    /**
     * Chart data has to be of type {@link XYChart.Series} due to JavaFX charts
     * requiring an {@link javafx.collections.ObservableList}, which is a type of List which keeps track
     * of updates, this is used for easily updating the data for the chart
     */
    private XYChart.Series<String, Number> chartNumInfected;
    private XYChart.Series<String, Number> chartNumDead;
    private final PandemicController controller; //Holds the controller for manipulating the View (GUI)

    /**
     * Constructors the pandemic panel object,
     * creating the time and the city
     */
    public PandemicModel(PandemicController controller) {
        //Initializations
        DELAY = 16;
        counter_paint = 0;
        counter_update = 0;
        this.controller = controller;
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


        //Worker timer, used for updating data structure, needed due to intensive workload
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (controller.isRunning()) { //If it's running
                    if (controller.isReset()) { //If the controller has told the model to reset
                        chartNumDead.getData().clear();
                        chartNumInfected.getData().clear();
                        start_time = System.currentTimeMillis();
                        fillConstants();
                        city = new City(constants);
                        counter_update = 0;
                        counter_paint = 0;
                        controller.turnResetOff();
                    }
                    update();

                    if (counter_update % 10 == 0)
                        update_chart();
                    counter_update++;
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
        double current_time = (start_time - System.currentTimeMillis()) / 1000.0;
        Platform.runLater(() -> {
            chartNumInfected.getData().add(new XYChart.Data<>(String.valueOf(current_time), city.getNum_infected()));
            chartNumDead.getData().add(new XYChart.Data<>(String.valueOf(current_time), city.getNum_dead()));
        });

    }

    private void fillConstants() {
        constants.width = (int) controller.getCanvas().getWidth();
        constants.height = (int) controller.getCanvas().getHeight();
        constants.infective_radius = controller.getInfective_r_amount();
        constants.symptoms_time = controller.getSymptoms_amount();
        constants.recovery_time = controller.getRecovery_amount();
        System.out.println(constants);
    }


    /**
     * Function that will be called every tick per delay time,
     * it will be used for painting the canvas based on the data
     * from a {@link City} instance
     *
     * @param canvas A kind of panel that will be used for painting
     */

    public void paint(Canvas canvas) {
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
    public void update() {

        //Updates the population
        for (int i = 0; i < city.getNumPeople(); i++) {
            city.getPopulation().get(i).update(city);
        }

        //Graph test
        if (counter_update % 100 == 0) {
            LinkedList<Person> lista_nodes = city.iterativePreOrder();
            for (Person p : lista_nodes) {
                System.out.print(p.toString() + ",");
            }

            System.out.println("\nN_Inf_Calc: " + lista_nodes.size() + ", N_Inf_Reais: " + city.getNum_infected());
        }
    }

}