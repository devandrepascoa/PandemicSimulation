module PandemicSimulation {
    requires javafx.controls;
    requires org.jfxtras.styles.jmetro;
    requires javafx.fxml;
    requires org.abego.treelayout.core;
    requires java.desktop;

    opens com.devandrepascoa.main to javafx.fxml;
    exports com.devandrepascoa.main;
    exports com.devandrepascoa.fxgraph.cells;
    exports com.devandrepascoa.fxgraph.edges;
    exports com.devandrepascoa.fxgraph.graph;
    exports com.devandrepascoa.fxgraph.layout;
    exports com.devandrepascoa.data_structure;
}