package com.devandrepascoa.data_structure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import com.devandrepascoa.main.Utils;
import com.devandrepascoa.main.PandemicModel;

import java.util.ArrayList;

/**
 * The com.data_structure.Person class provides the pillar for the whole program,
 * it will be the data structure for plotting the pandemic graph
 * due to it's infectedPeople attribute
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class Person extends Point {
    private State state;
    private final String name;
    private final int photo_id;
    /**
     * The references to all infected people, this provides essence
     * for creating the graph for which we will be able to plot
     * the pandemic growth
     */
    private ArrayList<Person> infectedPeople;

    //Attributes, Will change based on slider input
    private int time_since_infected;
    private boolean inHospital;

    //Variables shared between all objects of this instance
    //Basically they are the config variables, in the future
    //each individual person may have a different time
    //but for simplicity's sake we'll keep it like this
    public static int size;
    public static int symptoms_time;
    public static int recovery_time;
    public static int death_prob_hospital;
    public static int death_prob;
    public static int infective_radius;

    /**
     * @param name com.data_structure.Person's name, used for interactive purposes(non simulation)
     * @param x    Initial com.data_structure.Person's x coordinate
     * @param y    Initial com.data_structure.Person's y coordinate
     */
    public Person(String name, int x, int y) {
        super(x, y);
        this.state = State.SUSCEPTIBLE;
        this.name = name;
        this.infectedPeople = new ArrayList<>();
        this.inHospital = false;
        time_since_infected = 0;
        this.photo_id = Utils.randomGenerator(1, 100);

    }


    /**
     * Method that paints the {@link Person} to the canvas
     *
     * @param g Canvas Graphics Context
     */
    public void paint(GraphicsContext g) {
        //Color based on state
        switch (this.state) {
            case INFECTED_NO_SYMPTOMS:
                g.setFill(Color.PINK);
                break;
            case SUSCEPTIBLE:
                g.setFill(Color.WHITE);
                break;
            case RECOVERED:
                g.setFill(Color.GRAY);
                break;
            case INFECTED:
                g.setFill(Color.RED);
                break;
            case DEAD:
                g.setFill(Color.BLACK);
                break;
        }

        //Prints the person as an oval
        if (!inHospital)
            g.fillOval(
                    this.x,
                    this.y,
                    size, size);
    }

    /**
     * Function that updates variables like velocity,
     * state and others based on the game state
     *
     * @param city Map instance, used for collision detection
     *             and interacting with other people
     * @param width
     * @param height
     */
    public void update(City city, int width, int height) {
        //Handles state change after infection
        if (state != State.SUSCEPTIBLE && state != State.RECOVERED && state != State.DEAD) {
            this.time_since_infected += PandemicModel.DELAY;

            if (time_since_infected > symptoms_time) {
                this.state = State.INFECTED;
                if (!city.getHospital().isFull() && !inHospital) {
                    city.getHospital().addPerson(this);
                    inHospital = true;
                    this.dx = 0;
                    this.dy = 0;
                }
            }
            if (time_since_infected > recovery_time) {
                this.state = State.RECOVERED;
                int prob = Utils.randomGenerator(0, 99);
                if (inHospital) { //If in hospital, probability of death is different
                    if (prob < death_prob_hospital) {
                        this.state = State.DEAD;
                        this.dx = 0;
                        this.dy = 0;
                        city.addNumDead(1);
                    }
                } else if (prob < death_prob) { //10% Probability to die if person not in hospital
                    this.state = State.DEAD;
                    //If the person is dead, reset their velocities
                    this.dx = 0;
                    this.dy = 0;
                    city.addNumDead(1);
                }
            }
        }

        //Updates the person's location
        super.update(city, width, height);

        //Iterates through every person in the map
        //and checks for collisions with this instance
        //Only used for this instance to infect others
        //it's not two way
        if (!inHospital)
            for (int i = 0; i < city.getNumPeople(); i++) {
                Person person = city.getPopulation().get(i);
                if (this.collision(person) && this.isInfected()) {
                    if (person.getState() == State.SUSCEPTIBLE) {
                        person.setState(State.INFECTED_NO_SYMPTOMS);
                        this.addInfected(person);
                        city.addNumInfected(1);
                    }
                }
            }

    }

    /**
     * Function used for adding a person to the list
     * of "child" nodes, used for the representation
     * of infection based on a graph, where the initial
     * node is the first infected patient
     *
     * @param person {@link Person} instance
     */
    private void addInfected(Person person) {
        this.infectedPeople.add(person);
    }

    /**
     * Function to check whether a {@link Point} like
     * {@link Person} instance, is within the radius of this
     * instance's infective radius
     *
     * @param p2 {@link Person} instance
     * @return true if p2 is within radius of infection
     */
    public boolean collision(Person p2) {
        return this.withinRadius(p2, infective_radius);
    }


    /**
     * @return true if infected with symptoms or without them, else false
     */
    public boolean isInfected() {
        return (this.getState() == State.INFECTED || this.getState() == State.INFECTED_NO_SYMPTOMS);
    }

    public int getPhoto_id() {
        return photo_id;
    }


    /**
     * Enumeration containing all the states a person can have
     */
    public enum State {
        INFECTED, INFECTED_NO_SYMPTOMS, RECOVERED, SUSCEPTIBLE, DEAD
    }


    //ACCESSORS
    public String getName() {
        return name;
    }

    public ArrayList<Person> getInfectedPeople() {
        return infectedPeople;
    }

    public void setInfectedPeople(ArrayList<Person> infectedPeople) {
        this.infectedPeople = infectedPeople;
    }

    public boolean isInHospital() {
        return inHospital;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getTime_since_infected() {
        return time_since_infected;
    }

    public void setTime_since_infected(int time_since_infected) {
        this.time_since_infected = time_since_infected;
    }

    public void setInHospital(boolean inHospital) {
        this.inHospital = inHospital;
    }
}
