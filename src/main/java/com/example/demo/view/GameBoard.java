package com.example.demo.view;

import javafx.scene.Group;

public class GameBoard {
    private final Cell[][] grid;
    private final Group root;

    public GameBoard(Group root, double cellSize, double spacing) {
        this.root = root;
        this.grid = new Cell[4][4];
        createGrid(cellSize, spacing);
    }

    private void createGrid(double cellSize, double spacing) {
        // Implementation similar to your original GameScene
    }

    public void updateDisplay(Cell[][] grid) {
        // Update the visual representation
    }
}
