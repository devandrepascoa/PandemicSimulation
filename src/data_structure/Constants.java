package data_structure;

/**
 * Class with multiple "constants", they are not static,
 * due to the possibility of being accessed by multiple Threads,
 * and because of that an object of type {@link Constants} will be passed
 * around when creating a city, so only after reset will changes be applied(Intended use)
 */
public class Constants {
    public int width = 600;
    public int height = 600;
    public int recovery_time = 15000;
    public int symptoms_time = 10000;
    public int death_prob = 50;
    public int death_prob_hospital = 3;
    public int infective_radius = 10;
    public int num_people = 1000;

    @Override
    public String toString() {
        return "Constants{" +
                "width=" + width +
                ", height=" + height +
                ", recovery_time=" + recovery_time +
                ", symptoms_time=" + symptoms_time +
                ", death_prob=" + death_prob +
                ", death_prob_hospital=" + death_prob_hospital +
                ", infective_radius=" + infective_radius +
                ", num_people=" + num_people +
                '}';
    }
}
