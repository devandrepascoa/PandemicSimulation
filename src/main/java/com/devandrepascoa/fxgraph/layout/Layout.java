package com.devandrepascoa.fxgraph.layout;

import com.devandrepascoa.fxgraph.graph.Graph;

/**
 * Layout interface for abstracting layouts
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public interface Layout {

    /**
     * Adds the implemented layout into a graph.
     *
     * @param graph the graph
     */
    void execute(Graph graph);

}
