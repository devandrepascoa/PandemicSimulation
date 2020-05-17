package com.devandrepascoa.data_structure;

/**
 * Bed for a person, used in the hospital,
 * it's a wrapper for a {@link Person} object
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class Bed {
    private Person person;

    /**
     * Adds a person to this bed
     *
     * @param person a person
     * @throws BedNotEmptyException when bed is not empty
     */
    public void addPerson(Person person) throws BedNotEmptyException {
        if (!getFilled()) throw new BedNotEmptyException();
        this.person = person;
    }

    /**
     * Removes person from bed
     */
    public void removePerson() {
        this.person = null;
    }

    /**
     * @return true if bed has a person already
     */
    public boolean getFilled() {
        return person == null;
    }

    /**
     * Exception for when bed is not empty
     */
    public static class BedNotEmptyException extends RuntimeException {
        @Override
        public void printStackTrace() {
            super.printStackTrace();
            System.err.println("com.data_structure.Bed is not empty, remove person before inserting another");
        }
    }
}
