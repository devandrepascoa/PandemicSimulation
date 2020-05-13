package com.devandrepascoa.fxgraph.cells;

import com.devandrepascoa.fxgraph.graph.Graph;

import com.devandrepascoa.data_structure.Person;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PersonCell extends AbstractCell {

    private Color color;
    private Person person;

    public PersonCell(Person person) {
        color = Color.DODGERBLUE;
        this.person = person;
    }

    public PersonCell() {
        color = Color.DODGERBLUE;
    }

    @Override
    public Region getGraphic(Graph graph) {
        final Rectangle view = new Rectangle(50, 50);

        view.setStroke(color);
        view.setFill(color);

        final Pane pane = new Pane(view);
        pane.setPrefSize(50, 50);
        view.widthProperty().bind(pane.prefWidthProperty());
        view.heightProperty().bind(pane.prefHeightProperty());

        return pane;
    }

    public Person getPerson() {
        return person;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
