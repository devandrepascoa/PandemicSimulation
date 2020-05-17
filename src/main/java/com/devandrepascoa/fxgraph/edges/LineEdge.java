package com.devandrepascoa.fxgraph.edges;

import com.devandrepascoa.fxgraph.cells.Cell;
import javafx.scene.shape.Line;

/**
 * {@link Edge} implementation, creates a direct line between
 * the nodes
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class LineEdge extends Edge {
    public LineEdge(Cell source, Cell target) {
        super(source, target);

        line = new Line();

        line.startXProperty().bind(source.layoutXProperty().add(source.getBoundsInParent().getWidth() / 2.0));
        line.startYProperty().bind(source.layoutYProperty().add(source.getBoundsInParent().getHeight() / 2.0));

        line.endXProperty().bind(target.layoutXProperty().add(target.getBoundsInParent().getWidth() / 2.0));
        line.endYProperty().bind(target.layoutYProperty().add(target.getBoundsInParent().getHeight() / 2.0));

        getChildren().add(line);
    }

}
