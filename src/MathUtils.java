/**
 * Mathematical Functions
 * @author André Páscoa, André Carvalho
 * @version 1.0.0
 */
public class MathUtils {

    /**
     * Function for calculating random variable
     * @param limite_inf Minimum value
     * @param limite_sup Maximum value
     * @return a random value between limite_inf and limite_sup
     * which in a loop will provide a uniform distribution
     */
    public static int randomGenerator(int limite_inf, int limite_sup) {
        return (int) (limite_inf + (Math.random() * (limite_sup - limite_inf)));
    }

}
