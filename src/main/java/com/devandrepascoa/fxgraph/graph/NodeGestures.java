package com.devandrepascoa.fxgraph.graph;

import com.devandrepascoa.fxgraph.cells.PersonCell;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Class for adding selectable capability to a node
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class NodeGestures {

    final Graph graph;
    private static Node current_node;

    public NodeGestures(Graph graph) {
        this.graph = graph;
    }


    public void makeSelectable(final Node node) {
        node.setOnMousePressed(onMousePressedEventHandler);
    }

    public void makeUnselectable(final Node node) {
        node.setOnMousePressed(null);
    }

    /**
     * Mouse Pressed handler for each node
     */
    final EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                final PersonCell node = (PersonCell) event.getSource();
                graph.setSelected_cell(node);
                graph.update_info(node);
            }
            event.consume();
        }
    };

    //ACCESSORS

    public static Node getCurrent_node() {
        return current_node;
    }

    public static void setCurrent_node(Node current_node) {
        NodeGestures.current_node = current_node;
    }
}