package com.example.demo.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;  // Add this import for Arrays.equals()

public class GameModel {
    private long score = 0;
    private final int[][] board = new int[4][4];
    private boolean gameOver = false;

    public GameModel() {
        startNewGame();
    }

    public void startNewGame() {
        score = 0;
        gameOver = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = 0;
            }
        }
        addRandomTile();
        addRandomTile();
    }

    public boolean moveLeft() {
        boolean moved = false;
        for (int row = 0; row < 4; row++) {
            int[] newRow = moveRowLeft(board[row]);
            if (!Arrays.equals(board[row], newRow)) {
                board[row] = newRow;
                moved = true;
            }
        }
        return moved;
    }

    public boolean moveRight() {
        boolean moved = false;
        for (int row = 0; row < 4; row++) {
            int[] reversedRow = Arrays.copyOf(board[row], 4);
            reverseArray(reversedRow);
            int[] newRow = moveRowLeft(reversedRow);
            reverseArray(newRow);
            if (!Arrays.equals(board[row], newRow)) {
                board[row] = newRow;
                moved = true;
            }
        }
        return moved;
    }

    public boolean moveUp() {
        boolean moved = false;
        for (int col = 0; col < 4; col++) {
            int[] column = new int[4];
            for (int row = 0; row < 4; row++) {
                column[row] = board[row][col];
            }
            int[] newColumn = moveRowLeft(column);
            if (!Arrays.equals(column, newColumn)) {
                for (int row = 0; row < 4; row++) {
                    board[row][col] = newColumn[row];
                }
                moved = true;
            }
        }
        return moved;
    }

    public boolean moveDown() {
        boolean moved = false;
        for (int col = 0; col < 4; col++) {
            int[] column = new int[4];
            for (int row = 0; row < 4; row++) {
                column[row] = board[row][col];
            }
            reverseArray(column);
            int[] newColumn = moveRowLeft(column);
            reverseArray(newColumn);
            if (!Arrays.equals(column, newColumn)) {
                for (int row = 0; row < 4; row++) {
                    board[row][col] = newColumn[row];
                }
                moved = true;
            }
        }
        return moved;
    }

    private int[] moveRowLeft(int[] row) {
        // Remove zeros
        int[] newRow = new int[4];
        int pos = 0;
        for (int value : row) {
            if (value != 0) {
                newRow[pos++] = value;
            }
        }

        // Merge tiles
        for (int i = 0; i < 3; i++) {
            if (newRow[i] != 0 && newRow[i] == newRow[i + 1]) {
                newRow[i] *= 2;
                addScore(newRow[i]);
                newRow[i + 1] = 0;
            }
        }

        // Remove zeros again after merging
        int[] finalRow = new int[4];
        pos = 0;
        for (int value : newRow) {
            if (value != 0) {
                finalRow[pos++] = value;
            }
        }

        return finalRow;
    }

    private void reverseArray(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

    public void addRandomTile() {
        List<int[]> emptyPositions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    emptyPositions.add(new int[]{i, j});
                }
            }
        }
        if (!emptyPositions.isEmpty()) {
            Random random = new Random();
            int[] position = emptyPositions.get(random.nextInt(emptyPositions.size()));
            board[position[0]][position[1]] = Math.random() < 0.9 ? 2 : 4;
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public long getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void addScore(long points) {
        score += points;
    }

    public boolean hasWon() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasMovesLeft() {
        // Check if any empty cells
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 0) {
                    return true;
                }
            }
        }
        // Check for possible merges
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int value = board[i][j];
                // Check right
                if (j < 3 && board[i][j + 1] == value) {
                    return true;
                }
                // Check down
                if (i < 3 && board[i + 1][j] == value) {
                    return true;
                }
            }
        }
        return false;
    }
}