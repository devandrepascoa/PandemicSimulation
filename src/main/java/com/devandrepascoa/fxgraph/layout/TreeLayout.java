package com.devandrepascoa.fxgraph.layout;

import com.devandrepascoa.fxgraph.cells.Cell;
import com.devandrepascoa.fxgraph.graph.Graph;
import org.abego.treelayout.Configuration;
import org.abego.treelayout.NodeExtentProvider;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import java.awt.geom.Rectangle2D;
import java.util.Map;

/**
 * Class for creating the Tree Layout
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class TreeLayout implements Layout {

    private final Configuration<Cell> configuration;

    public TreeLayout() {
        this(100, 45, Configuration.Location.Top);
    }

    public TreeLayout(double gapBetweenLevels, double gapBetweenNodes, Configuration.Location location) {
        this(new DefaultConfiguration<Cell>(gapBetweenLevels, gapBetweenNodes, location));
    }

    public TreeLayout(Configuration<Cell> configuration) {
        this.configuration = configuration;
    }

    /**
     * Utilizes Abego Tree Layout library for updating X and Y varibles based on the relationships
     * between the nodes
     *
     * @param graph the desired graph
     */
    @Override
    public void execute(Graph graph) {
        final DefaultTreeForTreeLayout<Cell> layout = new DefaultTreeForTreeLayout<Cell>(graph.getModel().getRoot());
        addRecursively(layout, graph.getModel().getRoot());

        final NodeExtentProvider<Cell> nodeExtentProvider = new NodeExtentProvider<Cell>() {

            @Override
            public double getWidth(Cell tn) {
                if (tn == graph.getModel().getRoot()) {
                    return 0;
                }
                return tn.getWidth();
            }

            @Override
            public double getHeight(Cell tn) {
                if (tn == graph.getModel().getRoot()) {
                    return 0;
                }
                return tn.getHeight();
            }
        };
        final org.abego.treelayout.TreeLayout<Cell> treeLayout = new org.abego.treelayout.TreeLayout<>(layout, nodeExtentProvider, configuration);

        for (Map.Entry<Cell, Rectangle2D.Double> entry : treeLayout.getNodeBounds().entrySet()) {
            if (entry.getKey() != graph.getModel().getRoot()) {
                entry.getKey().setLayoutX(entry.getValue().getX());
                entry.getKey().setLayoutY(entry.getValue().getY());
            }
        }
        Cell root = graph.getModel().getRoot();
        graph.getPane().setLayoutX(root.getLayoutX());
        graph.getPane().setLayoutY(root.getLayoutY());
    }

    /**
     * Recursively creates a layout based on the tree connections,
     * has to use the Cell child nodes instead of the model {@link java.util.List}
     * due to requiring the relationship as in parent and child
     *
     * @param layout the abego tree layout
     * @param node   the selected node
     */
    public void addRecursively(DefaultTreeForTreeLayout<Cell> layout, Cell node) {
        for (Cell cell : node.getCellChildren()) {
            if (!layout.hasNode(cell)) {
                layout.addChild(node, cell);
                addRecursively(layout, cell);
            }
        }
    }

}