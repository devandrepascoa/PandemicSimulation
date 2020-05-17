package com.devandrepascoa.fxgraph.layout;

import com.devandrepascoa.fxgraph.cells.Cell;
import com.devandrepascoa.fxgraph.graph.Graph;

import java.util.List;
import java.util.Random;

/**
 * Class for creating a random layout for the graph
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class RandomLayout implements Layout {

    private final int WIDTH;
    private final int HEIGHT;

    Random rnd = new Random();

    public RandomLayout(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    @Override
    public void execute(Graph graph) {

        List<Cell> cells = graph.getModel().getAllCells();

        for (Cell cell : cells) {

            double x = rnd.nextDouble() * WIDTH;
            double y = rnd.nextDouble() * HEIGHT;

            cell.relocate(x, y);

        }

    }

}