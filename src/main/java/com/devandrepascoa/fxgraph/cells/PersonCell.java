package com.devandrepascoa.fxgraph.cells;

import com.devandrepascoa.data_structure.Person;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class for representing the {@link Person} object
 * as an actual node, basically a wrapper
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class PersonCell extends Cell {
    private final Person person;

    public PersonCell(Person person) {
        this.person = person;
        Rectangle view = new Rectangle(50, 50);

        setView(view);
        setColor(Color.DODGERBLUE);
    }

    public Person getPerson() {
        return person;
    }

    public void setColor(Color color) {
        Rectangle view = (Rectangle) this.view;
        view.setStroke(color);
        view.setFill(color);
    }
}
