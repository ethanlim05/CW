package com.example.demo.model;

import com.example.demo.view.Cell;

public class GameLogic {
    private final Cell[][] grid;
    private long score = 0;

    public GameLogic(Cell[][] grid) {
        this.grid = grid;
    }

    public void moveLeft() {
        // Implementation of move logic
    }

    public void moveRight() {
        // Implementation of move logic
    }

    public void moveUp() {
        // Implementation of move logic
    }

    public void moveDown() {
        // Implementation of move logic
    }

    public boolean isGameOver() {
        // Check if game is over
        return false;
    }

    public void addScore(long points) {
        score += points;
    }

    public long getScore() {
        return score;
    }
}
