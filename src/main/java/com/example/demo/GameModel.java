package com.example.demo;

import javafx.scene.input.KeyCode;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class GameModel {
    private long score = 0;
    private int[][] board = new int[4][4];
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

    public boolean moveUp() {
        boolean moved = false;
        for (int col = 0; col < 4; col++) {
            for (int row = 1; row < 4; row++) {
                if (board[row][col] != 0) {
                    int newPos = findNewPositionUp(row, col);
                    if (newPos != row) {
                        if (board[newPos][col] == 0) {
                            board[newPos][col] = board[row][col];
                            board[row][col] = 0;
                            moved = true;
                        } else if (board[newPos][col] == board[row][col]) {
                            board[newPos][col] *= 2;
                            addScore(board[newPos][col]);
                            board[row][col] = 0;
                            moved = true;
                        }
                    }
                }
            }
        }
        return moved;
    }

    public boolean moveDown() {
        boolean moved = false;
        for (int col = 0; col < 4; col++) {
            for (int row = 2; row >= 0; row--) {
                if (board[row][col] != 0) {
                    int newPos = findNewPositionDown(row, col);
                    if (newPos != row) {
                        if (board[newPos][col] == 0) {
                            board[newPos][col] = board[row][col];
                            board[row][col] = 0;
                            moved = true;
                        } else if (board[newPos][col] == board[row][col]) {
                            board[newPos][col] *= 2;
                            addScore(board[newPos][col]);
                            board[row][col] = 0;
                            moved = true;
                        }
                    }
                }
            }
        }
        return moved;
    }

    public boolean moveLeft() {
        boolean moved = false;
        for (int row = 0; row < 4; row++) {
            for (int col = 1; col < 4; col++) {
                if (board[row][col] != 0) {
                    int newPos = findNewPositionLeft(row, col);
                    if (newPos != col) {
                        if (board[row][newPos] == 0) {
                            board[row][newPos] = board[row][col];
                            board[row][col] = 0;
                            moved = true;
                        } else if (board[row][newPos] == board[row][col]) {
                            board[row][newPos] *= 2;
                            addScore(board[row][newPos]);
                            board[row][col] = 0;
                            moved = true;
                        }
                    }
                }
            }
        }
        return moved;
    }

    public boolean moveRight() {
        boolean moved = false;
        for (int row = 0; row < 4; row++) {
            for (int col = 2; col >= 0; col--) {
                if (board[row][col] != 0) {
                    int newPos = findNewPositionRight(row, col);
                    if (newPos != col) {
                        if (board[row][newPos] == 0) {
                            board[row][newPos] = board[row][col];
                            board[row][col] = 0;
                            moved = true;
                        } else if (board[row][newPos] == board[row][col]) {
                            board[row][newPos] *= 2;
                            addScore(board[row][newPos]);
                            board[row][col] = 0;
                            moved = true;
                        }
                    }
                }
            }
        }
        return moved;
    }

    private int findNewPositionUp(int row, int col) {
        int newPos = row;
        while (newPos > 0 && board[newPos - 1][col] == 0) {
            newPos--;
        }
        return newPos;
    }

    private int findNewPositionDown(int row, int col) {
        int newPos = row;
        while (newPos < 3 && board[newPos + 1][col] == 0) {
            newPos++;
        }
        return newPos;
    }

    private int findNewPositionLeft(int row, int col) {
        int newPos = col;
        while (newPos > 0 && board[row][newPos - 1] == 0) {
            newPos--;
        }
        return newPos;
    }

    private int findNewPositionRight(int row, int col) {
        int newPos = col;
        while (newPos < 3 && board[row][newPos + 1] == 0) {
            newPos++;
        }
        return newPos;
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

    private boolean hasWon() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasMovesLeft() {
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
