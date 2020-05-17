module PandemicSimulation {
    requires java.base;
    requires java.desktop;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.fxml;
    requires org.jfxtras.styles.jmetro;

    opens com.devandrepascoa.main to javafx.fxml;
    exports com.devandrepascoa.main;
    exports com.devandrepascoa.fxgraph.cells;
    exports com.devandrepascoa.fxgraph.edges;
    exports com.devandrepascoa.fxgraph.graph;
    exports com.devandrepascoa.fxgraph.layout;
    exports com.devandrepascoa.data_structure;

    exports org.abego.treelayout;
    exports org.abego.treelayout.util;
    exports org.abego.treelayout.internal.util;
    exports org.abego.treelayout.internal.util.java.util;
    exports org.abego.treelayout.internal.util.java.lang;
    exports org.abego.treelayout.internal.util.java.lang.string;
}