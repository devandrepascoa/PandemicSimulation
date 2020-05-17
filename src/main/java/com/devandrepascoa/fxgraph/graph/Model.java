package com.devandrepascoa.fxgraph.graph;

import com.devandrepascoa.data_structure.Person;
import com.devandrepascoa.fxgraph.cells.Cell;
import com.devandrepascoa.fxgraph.cells.PersonCell;
import com.devandrepascoa.fxgraph.cells.RectangleCell;
import com.devandrepascoa.fxgraph.edges.Edge;
import com.devandrepascoa.fxgraph.edges.LineEdge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Model of the Tree, holds the tree as a {@link List} of cells
 * instead of a reference tree representation due to performance
 * requirements
 *
 * @author André Páscoa, André Carvalho
 * @version 2.1.0
 */
public class Model {

    public Cell getRoot() {
        return root;
    }

    private final Cell root;
    private Cell real_root;

    private ObservableList<Cell> allCells;
    private ObservableList<Edge> allEdges;


    public Model() {

        root = new RectangleCell();

        // clear model, create lists
        clear();
    }

    public void clear() {
        allCells = FXCollections.observableArrayList();
        allEdges = FXCollections.observableArrayList();
    }

    public ObservableList<Cell> getAllCells() {
        return allCells;
    }

    public ObservableList<Edge> getAllEdges() {
        return allEdges;
    }

    public void addCell(Cell cell) {
        allCells.add(cell);
    }

    public void addEdge(Edge edge) {
        allEdges.add(edge);
    }

    /**
     * Attach all cells which don't have a parent to graphParent
     *
     * @param cellList
     */
    public void attachOrphansToGraphParent(List<Cell> cellList) {
        for (Cell cell : cellList) {
            if (cell.getCellParents().size() == 0) {
                root.addCellChild(cell);
            }
        }
    }


    /**
     * Converts the Person tree, into a graph model,
     * which is a list of nodes combined with a list of edges(connections)
     *
     * @param root node for the Person tree
     */
    public void fromPersonTree(Person root) {
        PersonCell root_cell = new PersonCell(root);
        root_cell.setColor(Color.RED);
        real_root = root_cell;
        addCell(root_cell);
        fromPersonTree(root_cell);
    }

    private void fromPersonTree(PersonCell cell) {
        Person person = cell.getPerson();

        for (int i = 0; i < person.getInfectedPeople().size(); i++) {
            PersonCell new_cell = new PersonCell(person.getInfectedPeople().get(i));
            fromPersonTree(new_cell);
            addCell(new_cell);
            addEdge(new LineEdge(cell, new_cell));
        }
    }

    public Cell getReal_root() {
        return real_root;
    }
}