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

    private final Person zero_day;
    private ArrayList<Person> population;
    private int width;
    private int height;
    private int num_infected = 1; //Starts with 1 due to the zero day patient

    public City(int width, int height, int num_people) {
        this.width = width;
        this.height = height;
        population = new ArrayList<>();
        //Creating the first patient
        zero_day = new Person("Pablo Escobar", width / 2, height / 2);
        zero_day.setState(Person.State.INFECTED_NO_SYMPTOMS);
        population.add(zero_day);

        //Generating a random distribution of num_people-1
        for (int i = 1; i < num_people; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);

            population.add(new Person("Pablo Escobar", x, y));
        }
    }


    /**
     * @return returns a list of a pre order depth first traversal of the infected people graph
     */
    public LinkedList<Person> preOrderIterativo() {

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
            //Lista reversed devido a querer iterar da esquerda para a direita
            for (int i = no.getInfectedPeople().size() - 1; i >= 0; i--) {
                nosVisitar.push(no.getInfectedPeople().get(i));
            }
            counter++;
        }

        return nosVisitados;
    }

    public void addNumInfected(int i){
        this.num_infected+= i;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Person getZero_day() {
        return zero_day;
    }

    public int getHeight() {
        return height;
    }


    public int getNum_infected() {
        return num_infected;
    }
}
