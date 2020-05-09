import javax.swing.*;

/**
 * Pandemic Simulator
 * @author André Páscoa, André Carvalho
 * @version 1.0.0
 */
public class PandemicSimulator {
    private static final int WIDTH = 600; //Initial Frame width
    private static final int HEIGHT = 600; //Initial Frame height

    //Main method, starts up the frame and places the canvas inside
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pandemic Simulator");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SimulationPanel());
        frame.setVisible(true);
    }

}
