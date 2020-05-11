package com.fxgraph.graph;

import com.fxgraph.cells.PersonCell;
import com.fxgraph.layout.AbegoTreeLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.abego.treelayout.Configuration;

import java.util.LinkedList;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        final Pane root = new Pane();

        Graph tree = new Graph();
        addTreeComponents(tree);
        root.getChildren().add(tree.getCanvas());

        final Scene scene = new Scene(root, 1024, 768);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addTreeComponents(Graph graph) {
        final Model model = graph.getModel();
        graph.beginUpdate();

        int size = 5;
        LinkedList<ICell> cells = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            cells.add(new PersonCell());
            model.addCell(cells.get(i));
        }
        for (int i = 1; i < size; i++) {
            model.addEdge(cells.get(0), cells.get(i));
        }

        graph.endUpdate();
        graph.layout(new AbegoTreeLayout(200, 200, Configuration.Location.Top));
    }


    public static void main(String[] args) {
        launch(args);
    }
}