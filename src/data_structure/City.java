package data_structure;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Class containing the data structure for the population
 *
 * @author André Páscoa, André Carvalho
 * @version 1.0.0
 */
public class City {
    private final Constants constants;
    private final Person zero_day;
    private ArrayList<Person> population;
    private final Hospital hospital; //data_structure.City's hospital
    //Starts with 1 due to the zero day patient
    //used so it's not necessary to calculate the infected
    //people graph every time we require the number of infected
    private int num_infected = 1;
    private int num_dead = 0;


    public City(Constants constants) {
        this.constants = constants;
        hospital = new Hospital(1);
        population = new ArrayList<>();
        //Creating the first patient
        zero_day = new Person("Pablo Escobar", constants.width / 2, constants.height / 2, constants);
        zero_day.setState(Person.State.INFECTED_NO_SYMPTOMS);
        population.add(zero_day);

        //Generating a random distribution of num_people-1
        for (int i = 1; i < constants.num_people; i++) {
            int x = (int) (Math.random() * constants.width);
            int y = (int) (Math.random() * constants.height);

            population.add(new Person("Pablo Escobar", x, y, constants));
        }
    }


    /**
     * @return returns a list of a pre order depth first traversal of the infected people graph
     */
    public LinkedList<Person> iterativePreOrder() {

        LinkedList<Person> nosVisitados = new LinkedList<>();

        Stack<Person> nosVisitar = new Stack<>();
        nosVisitar.push(zero_day);

        Person no;
        int counter = 0;
        while (!nosVisitar.empty() && counter < 100000) {
            no = nosVisitar.pop();
            if (no == null)
                continue;
            nosVisitados.add(no);

            for (int i = no.getInfectedPeople().size() - 1; i >= 0; i--) {
                nosVisitar.push(no.getInfectedPeople().get(i));
            }
            counter++;
        }

        return nosVisitados;
    }

    /**
     * Method to increase the number of infected people,
     * so it's less costly in computational terms
     *
     * @param i A value
     */
    public void addNumInfected(int i) {
        this.num_infected += i;
    }

    /**
     * Method to increase the variable containing the number
     * of dead people, so it's less costly in computational terms
     *
     * @param i A value
     */
    public void addNumDead(int i) {
        this.num_dead += i;
    }

    /**
     * Method used for painting the city on to a canvas
     *
     * @param g Canvas graphics context, used for painting
     */
    public void paint(GraphicsContext g) {
        for (Person person : population) {
            person.paint(g);
        }
    }

    //Accessors
    public int getNumPeople() {
        return this.population.size();
    }

    public ArrayList<Person> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Person> population) {
        this.population = population;
    }

    public Person getZero_day() {
        return zero_day;
    }

    public int getNum_infected() {
        return num_infected;
    }

    public Hospital getHospital() {
        return hospital;
    }


    public int getNum_dead() {
        return num_dead;
    }


    public Constants getConstants() {
        return constants;
    }
}
