package data_structure;

/**
 * Class with multiple "constants", they are not static,
 * due to the possibility of being accessed by multiple Threads,
 * and because of that an object of type {@link Constants} will be passed
 * around when creating a city, so only after reset will changes be applied(Intended use)
 *
 * @author André Páscoa, André Carvalho
 * @version 2.5.0
 */
public class Constants {
    public int size = 5;
    public int width = 600;
    public int height = 600;
    public int hospital_capacity = 0;
    public int recovery_time = 15000;
    public int symptoms_time = 10000;
    public int death_prob = 50;
    public int death_prob_hospital = 3;
    public int infective_radius = 10;
    public int num_people = 100;

    @Override
    public String toString() {
        return "Constants{" +
                "size=" + size +
                ", width=" + width +
                ", height=" + height +
                ", hospital_capacity=" + hospital_capacity +
                ", recovery_time=" + recovery_time +
                ", symptoms_time=" + symptoms_time +
                ", death_prob=" + death_prob +
                ", death_prob_hospital=" + death_prob_hospital +
                ", infective_radius=" + infective_radius +
                ", num_people=" + num_people +
                '}';
    }
}
