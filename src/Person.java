import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * The Person class provides the pillar for the whole program,
 * it will be the data structure for plotting the pandemic graph
 * due to it's infectedPeople attribute
 *
 * @author André Páscoa, André Carvalho
 * @version 1.0.0
 * TODO
 * -Faster non Ellipse implementation of distance calculation
 * -Adding Hospitals with beds
 */
public class Person extends Point {

    private State state;
    private final String name;
    /**
     * The references to all infected people, this provides essence
     * for creating the graph for which we will be able to plot
     * the pandemic growth
     */
    private ArrayList<Person> infectedPeople;

    //Attributes, Will change based on slider input
    private int size = 5;
    private double infectiveRadius = 10;
    private int time_since_infected = 0;
    private int recovery_time = 2000;
    private int symptoms_time = 700;
    private int num_infected = 0;

    /**
     * @param name Person's name, used for interactive purposes(non simulation)
     * @param x    Initial Person's x coordinate
     * @param y    Initial Person's y coordinate
     */
    public Person(String name, int x, int y) {
        super(x, y);
        this.state = State.SUSCEPTIBLE;
        this.name = name;
        this.infectedPeople = new ArrayList<>();
    }


    /**
     * Method that paints the Person to the canvas
     *
     * @param g Canvas Graphics Context
     */
    public void paint(Graphics g) {
        //Color based on state
        g.setColor(Color.BLACK);
        switch (this.state) {
            case INFECTED_NO_SYMPTOMS:
                g.setColor(Color.RED);
                break;
            case SUSCEPTIBLE:
                g.setColor(Color.BLACK);
                break;
            case RECOVERED:
                g.setColor(Color.GRAY);
                break;
            case INFECTED:
                g.setColor(Color.GREEN);
                break;
        }

        //Person painted as an oval
        g.fillOval(
                this.x,
                this.y,
                size, size);
        g.setColor(Color.BLACK);
    }

    /**
     * Function that updates variables like velocity
     * based on the game state
     *
     * @param city Map instance, used for collision detection
     *             and interacting with other people
     */
    public void update(City city) {
        //Handles state change after infection
        if (state != State.SUSCEPTIBLE && state != State.RECOVERED) {
            this.time_since_infected += SimulationPanel.DELAY;

            if (time_since_infected > symptoms_time) {
                this.state = State.INFECTED;
            }
            if (time_since_infected > recovery_time) {
                this.state = State.RECOVERED;
            }
        }

        //Updates the person's location
        super.update(city);

        //Iterates through every person in the map
        //and checks for collisions with this instance
        for (int i = 0; i < city.getNumPeople(); i++) {
            Person person = city.getPopulation().get(i);
            if (this.collision(person) && this.isInfected()) {
                if (!person.isInfected() && person.getState() != State.RECOVERED) {
                    person.setState(State.INFECTED_NO_SYMPTOMS);
                    this.addInfected(person);
                    num_infected++;
                }
            }
        }

    }

    /**
     * Function used for adding a person to the list
     * of "child" nodes, used for the representation
     * of infection based on a graph, where the initial
     * node is the first infect patient
     *
     * @param person {@link Person} instance
     */
    private void addInfected(Person person) {
        this.infectedPeople.add(person);
    }

    /**
     * Function to check whether a {@link Point} like
     * Person instance, is within the radius of this
     * instance's infective radius
     *
     * @param p2 {@link Person} instance
     * @return true if both circles collide, else false
     */
    public boolean collision(Person p2) {
        Ellipse2D rec2 = new Ellipse2D.Double(x, y, infectiveRadius, infectiveRadius);
        return rec2.contains(p2.getX(), p2.getY());
    }


    /**
     * @return true if infected with symptoms or without them, else false
     */
    public boolean isInfected() {
        return (this.getState() == State.INFECTED || this.getState() == State.INFECTED_NO_SYMPTOMS);
    }

    /**
     * Enumeration containing all the states a person can have
     */
    public enum State {
        INFECTED, INFECTED_NO_SYMPTOMS, RECOVERED, SUSCEPTIBLE
    }

    //Accessors

    public String getName() {
        return name;
    }

    public ArrayList<Person> getInfectedPeople() {
        return infectedPeople;
    }

    public void setInfectedPeople(ArrayList<Person> infectedPeople) {
        this.infectedPeople = infectedPeople;
    }

    public double getInfectiveRadius() {
        return infectiveRadius;
    }

    public void setInfectiveRadius(double infectiveRadius) {
        this.infectiveRadius = infectiveRadius;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
