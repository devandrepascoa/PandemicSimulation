package com.devandrepascoa.main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Utility functions
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class Utils {

    /**
     * Function for calculating random variable
     *
     * @param limite_inf Minimum value
     * @param limite_sup Maximum value
     * @return a random value between limite_inf and limite_sup
     * which in a loop will provide a uniform distribution
     */
    public static int randomGenerator(int limite_inf, int limite_sup) {
        return (int) Math.round((limite_inf + (Math.random() * (limite_sup - limite_inf))));
    }

    /**
     * Function for rounding a number
     *
     * @param value     value to round
     * @param precision number of decimal cases
     * @return rounded value
     */
    public static double getRoundedNumber(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    /**
     * Converts value to {@link String} with max_digits - digits 0's
     * to the left
     *
     * @param value      integer to convert
     * @param max_digits max digits for string
     * @return converted number
     * @throws RuntimeException, if num digits in value > max_digits
     */
    public static String intToString(int value, int max_digits) throws RuntimeException {
        //Handling exception case of value == 0
        StringBuilder temp_string = new StringBuilder();
        for (int i = 0; i < max_digits; i++) temp_string.append("0");
        if (value == 0) return temp_string.toString();

        //Transforms int to string with remaining left 0's
        int num_digits = 0;
        int value_temp = Math.abs(value);
        while (value_temp > 0) {
            num_digits++;
            value_temp = value_temp / 10;
        }
        int dif = max_digits - num_digits;

        if (dif < 0) throw new RuntimeException("Value has too many digits");
        StringBuilder value_string = new StringBuilder();
        for (int i = 0; i < dif; i++)
            value_string.append("0");
        value_string.append(Math.abs(value));

        return value_string.toString();
    }

    /**
     * Generates a random name from {@link PandemicSimulator} static
     * first_name and last_name cache
     *
     * @return random name, any language
     */
    public static String getRandomName() {
        String first_name = randomChoice(PandemicSimulator.getFirst_names());
        String last_name = randomChoice(PandemicSimulator.getLast_names());
        first_name = first_name.substring(0, 1).toUpperCase() + first_name.substring(1);
        last_name = last_name.substring(0, 1).toUpperCase() + last_name.substring(1);
        return first_name + " " + last_name;
    }

    /**
     * Generates an {@link ArrayList<String>} of all the lines a file
     *
     * @param url .txt file path, must be absolute
     * @return A list of all the lines in the file
     * @throws IOException, if file is not found
     */
    public static ArrayList<String> readAllFileIntoArray(URL url) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        Scanner scan = new Scanner(url.openStream());
        while (scan.hasNextLine()) {
            list.add(scan.nextLine());
        }
        return list;
    }

    /**
     * Returns a random line from a {@link ArrayList<String>} object
     *
     * @param array array containing a list of strings(typically lines from a file)
     * @return a random line
     */
    public static String randomChoice(ArrayList<String> array) {
        java.util.Random random = new java.util.Random();
        int index = random.nextInt(array.size());
        return array.get(index);
    }
}
