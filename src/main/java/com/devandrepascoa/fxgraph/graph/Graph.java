package com.devandrepascoa.fxgraph.graph;

import com.devandrepascoa.fxgraph.cells.Cell;
import com.devandrepascoa.fxgraph.cells.PersonCell;
import com.devandrepascoa.main.GraphController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Graph class that holds the pane containing all the nodes and edges.
 * Main class for the graph visualization operation
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class Graph {

    private final GraphController controller; //Graph controller for settings selected person
    private Cell selected_cell; //currently selected cell
    private final Model model;
    private PannablePane pannablePane;
    private NodeGestures nodeGestures;
    private ViewportGestures viewportGestures;
    private BooleanProperty useNodeGestures;
    private BooleanProperty useViewportGestures;


    public Graph(GraphController controller) {
        this.controller = controller;
        this.model = new Model();

        addListeners();
    }

    /**
     * Method that adds the listeners(Zooming, panning, selecting nodes) to the {@link PannablePane}
     */
    private void addListeners() {
        nodeGestures = new NodeGestures(this);
        useNodeGestures = new SimpleBooleanProperty(true);
        useNodeGestures.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                for (Cell cell : model.getAllCells()) {
                    nodeGestures.makeSelectable(cell);
                }
            } else {
                for (Cell cell : model.getAllCells()) {
                    nodeGestures.makeUnselectable(cell);
                }
            }
        });

        pannablePane = new PannablePane();
        viewportGestures = new ViewportGestures(pannablePane);
        useViewportGestures = new SimpleBooleanProperty(true);
        useViewportGestures.addListener((obs, oldVal, newVal) -> {
            final Parent parent = pannablePane.parentProperty().get();
            if (parent == null) {
                return;
            }
            if (newVal) {
                parent.addEventHandler(MouseEvent.MOUSE_PRESSED, viewportGestures.getOnMousePressedEventHandler());
                parent.addEventHandler(MouseEvent.MOUSE_DRAGGED, viewportGestures.getOnMouseDraggedEventHandler());
                parent.addEventHandler(ScrollEvent.ANY, viewportGestures.getOnScrollEventHandler());
            } else {
                parent.removeEventHandler(MouseEvent.MOUSE_PRESSED, viewportGestures.getOnMousePressedEventHandler());
                parent.removeEventHandler(MouseEvent.MOUSE_DRAGGED, viewportGestures.getOnMouseDraggedEventHandler());
                parent.removeEventHandler(ScrollEvent.ANY, viewportGestures.getOnScrollEventHandler());
            }
        });
        pannablePane.parentProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal != null) {
                oldVal.removeEventHandler(MouseEvent.MOUSE_PRESSED, viewportGestures.getOnMousePressedEventHandler());
                oldVal.removeEventHandler(MouseEvent.MOUSE_DRAGGED, viewportGestures.getOnMouseDraggedEventHandler());
                oldVal.removeEventHandler(ScrollEvent.ANY, viewportGestures.getOnScrollEventHandler());
            }
            if (newVal != null) {
                newVal.addEventHandler(MouseEvent.MOUSE_PRESSED, viewportGestures.getOnMousePressedEventHandler());
                newVal.addEventHandler(MouseEvent.MOUSE_DRAGGED, viewportGestures.getOnMouseDraggedEventHandler());
                newVal.addEventHandler(ScrollEvent.ANY, viewportGestures.getOnScrollEventHandler());
            }
        });
    }


    /**
     * Function that has to be called after the model has been initialized
     */
    public void endUpdate() {

        // add components to graph pane
        pannablePane.getChildren().addAll(model.getAllEdges());
        pannablePane.getChildren().addAll(model.getAllCells());

        for (Cell cell : getModel().getAllCells()) {
            if (useNodeGestures.get()) {
                nodeGestures.makeSelectable(cell);
            }
        }

        // every cell must have a parent, if it doesn't, then the graphParent is
        // the parent
        getModel().attachOrphansToGraphParent(model.getAllCells());

        this.selected_cell = getModel().getReal_root();
        update_info((PersonCell) getModel().getReal_root());
    }


    /**
     * Updates the currently selected cell in the graph
     *
     * @param selected_cell selected cell
     */
    public void setSelected_cell(Cell selected_cell) {
        this.selected_cell.setColor(Color.DODGERBLUE);
        this.selected_cell = selected_cell;
        this.selected_cell.setColor(Color.RED);
    }

    /**
     * Updates the {@link GraphController} selected person
     *
     * @param node selected person cell
     */
    public void update_info(PersonCell node) {
        this.controller.setSelected_person(node);
    }

    //ACCESSORS

    public Pane getPane() {
        return this.pannablePane;
    }


    public Model getModel() {
        return model;
    }

    public double getScale() {
        return this.pannablePane.getScale();
    }

    public Cell getSelected_cell() {
        return selected_cell;
    }
}