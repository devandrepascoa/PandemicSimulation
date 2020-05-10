package data_structure;

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
            System.err.println("data_structure.Bed is not empty, remove person before inserting another");
        }
    }
}
