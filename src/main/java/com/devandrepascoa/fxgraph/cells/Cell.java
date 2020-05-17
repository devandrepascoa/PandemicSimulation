package com.devandrepascoa.fxgraph.cells;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating a cell
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public abstract class Cell extends Pane {
    protected final List<Cell> children = new ArrayList<>();
    protected final List<Cell> parents = new ArrayList<>();
    protected Node view;

    //ACCESSORS

    public void addCellChild(Cell cell) {
        children.add(cell);
    }

    public List<Cell> getCellChildren() {
        return children;
    }

    public void addCellParent(Cell cell) {
        parents.add(cell);
    }

    public List<Cell> getCellParents() {
        return parents;
    }

    public void removeCellChild(Cell cell) {
        children.remove(cell);
    }

    public void setView(Node view) {
        this.view = view;
        getChildren().add(view);
    }

    public Node getView() {
        return this.view;
    }

    public abstract void setColor(Color color);
}