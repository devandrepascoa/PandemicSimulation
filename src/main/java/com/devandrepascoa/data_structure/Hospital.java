package com.devandrepascoa.data_structure;

import java.util.ArrayList;

/**
 * Used for creating the hospital, which by itself
 * is not visualized, but will have the effect of isolating
 * people that become infected with symptoms
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class Hospital {
    private final ArrayList<Bed> beds;
    private int num_filled_beds; //Used for keeping track of filled beds

    public Hospital(int capacity) {
        beds = new ArrayList<>();
        this.num_filled_beds = 0;
        for (int i = 0; i < capacity; i++) {
            beds.add(new Bed());
        }
    }

    /**
     * @return true if hospital is full
     */
    public boolean isFull() {
        return num_filled_beds == beds.size();
    }

    /**
     * Adds a person to the hospital
     *
     * @param person person instance to add
     * @throws HospitalFullException if the hospital is full
     */
    public void addPerson(Person person) throws HospitalFullException {
        if (isFull()) throw new HospitalFullException();
        this.beds.get(num_filled_beds).addPerson(person);
        num_filled_beds++;
    }

    /**
     * Exception which will be thrown in case of hospital beds being full
     */
    public static class HospitalFullException extends RuntimeException {
        @Override
        public void printStackTrace() {
            super.printStackTrace();
            System.err.println("com.data_structure.Hospital is full!");
        }
    }

    //ACCESSORS

    public int getNum_filled_beds() {
        return num_filled_beds;
    }
}
