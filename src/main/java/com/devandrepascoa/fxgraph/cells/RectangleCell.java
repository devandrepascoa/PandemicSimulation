package com.devandrepascoa.fxgraph.cells;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


/**
 * {@link Cell} implementation example
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class RectangleCell extends Cell {

    public RectangleCell() {
        this(50, 50);
    }

    public RectangleCell(int width, int height) {
        Rectangle view = new Rectangle(width, height);
        setView(view);
        setColor(Color.DODGERBLUE);
    }

    @Override
    public void setColor(Color color) {
        Rectangle view = (Rectangle) this.view;
        view.setStroke(color);
        view.setFill(color);
    }


}