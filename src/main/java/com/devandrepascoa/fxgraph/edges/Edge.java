package com.devandrepascoa.fxgraph.edges;

import com.devandrepascoa.fxgraph.cells.Cell;
import javafx.scene.Group;
import javafx.scene.shape.Line;

/**
 * Abstract class for all types of connections
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public abstract class Edge extends Group {

    protected Cell source;
    protected Cell target;
    protected Line line;

    public Edge(Cell source, Cell target) {

        this.source = source;
        this.target = target;

        source.addCellChild(target);
        target.addCellParent(source);
    }

    public Cell getSource() {
        return source;
    }

    public Cell getTarget() {
        return target;
    }

}