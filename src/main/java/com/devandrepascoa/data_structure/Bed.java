package com.devandrepascoa.data_structure;

/**
 * Bed for a person, used in the hospital,
 * it's a wrapper for person
 *
 * @author André Páscoa, André Carvalho
 * @version 2.5.0
 */
public class Bed {
    private Person person;

    public boolean getFilled() {
        return person == null;
    }

    public void addPerson(Person person) {
        if (this.person != null) throw new BedNotEmptyException();
        this.person = person;
    }

    private static class BedNotEmptyException extends RuntimeException {
        @Override
        public void printStackTrace() {
            super.printStackTrace();
            System.err.println("com.data_structure.Bed is not empty, remove person before inserting another");
        }
    }
}
