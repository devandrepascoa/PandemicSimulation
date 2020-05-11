package models_controllers_others;

/**
 * Mathematical Functions
 *
 * @author André Páscoa, André Carvalho
 * @version 2.5.0
 */
public class MathUtils {

    /**
     * Function for calculating random variable
     *
     * @param limite_inf Minimum value
     * @param limite_sup Maximum value
     * @return a random value between limite_inf and limite_sup
     * which in a loop will provide a uniform distribution
     */
    public static int randomGenerator(int limite_inf, int limite_sup) {
        return (int) (limite_inf + (Math.random() * (limite_sup - limite_inf)));
    }

    /**
     * @param value value to round
     * @param precision number of decimal cases
     * @return rounded value
     */
    public static double getRoundedNumber(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

}
