package data_structure;

import java.util.ArrayList;

/**
 * Used for creating the hospital, which by itself
 * is not visualized, but will have the effect of isolating
 * people that become infected with symptoms
 * @author André Páscoa, André Carvalho
 * @version 2.5.0
 */
public class Hospital {
    private final ArrayList<Bed> beds;
    private int num_filled_beds;

    public Hospital(int capacity) {
        beds = new ArrayList<>();
        this.num_filled_beds = 0;
        for (int i = 0; i < capacity; i++) {
            beds.add(new Bed());
        }
    }

    public boolean isFull() {
        return num_filled_beds == beds.size();
    }

    public void addPerson(Person person) {
        if (isFull()) throw new HospitalFullException();
        this.beds.get(num_filled_beds).addPerson(person);
        num_filled_beds++;
    }

    private static class HospitalFullException extends RuntimeException {
        @Override
        public void printStackTrace() {
            super.printStackTrace();
            System.err.println("data_structure.Hospital is full!");
        }
    }

    public int getNum_filled_beds() {
        return num_filled_beds;
    }
}
