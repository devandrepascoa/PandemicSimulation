import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * This class will provide the canvas for the animation
 *
 * @author André Páscoa, André Carvalho
 * TODO
 * -Faster animations
 */
public class SimulationPanel extends JPanel implements ActionListener {

    public static final int DELAY = 16; //Update delay(Will update every 16 milliseconds)
    public static int num_people = 500;
    private final City city;
    private int tick = 0;

    /**
     * Constructors the pandemic panel object,
     * creating the time and the city
     */
    public SimulationPanel(int width, int height) {
        city = new City(width, height, num_people);
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    /**
     * Function that will be called every tick per delay time,
     * it will be used for painting the canvas based on the data
     * from a {@link City} instance
     *
     * @param g , Graphics context, used for painting the screen
     *          and getting canvas size;
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < city.getNumPeople(); i++) {
            Person person = city.getPopulation().get(i);
            person.paint(g);
        }
    }

    /**
     * Method that the {@link Timer} instance calls every
     * tick per delay time
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        tick++;
        //Graph test
        if (tick % 100 == 0) {
            LinkedList<Person> lista_nodes = city.preOrderIterativo();
            for (Person p : lista_nodes) {
                System.out.print(p.toString() + ",");
            }

            System.out.println("\nN_Inf_Calc: " + lista_nodes.size()+", N_Inf_Reais: "+city.getNum_infected());
        }
        repaint();
    }

    /**
     * Method that updates all the variables from the {@link City} instance
     * data structure.
     */
    public void update() {
        //Dynamically adjusts the city size based
        //on the current canvas size
        city.setHeight(this.getBounds().height);
        city.setWidth(this.getBounds().width);


        //Updates the population
        for (int i = 0; i < city.getNumPeople(); i++) {
            city.getPopulation().get(i).update(city);
        }

    }
}