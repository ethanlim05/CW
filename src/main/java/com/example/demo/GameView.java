package com.example.demo;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends Group {
    private static final int GRID_SIZE = 4;
    private static final int CELL_SIZE = 100;
    private static final int CELL_SPACING = 10;
    private static final Color BACKGROUND_COLOR = Color.rgb(189, 177, 92);
    private final Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
    private long score = 0;
    private boolean gameOver = false;
    private Scene scene;

    public GameView() {
        initializeGame();
        // Create the scene reference
    }

    // Add this method instead of trying to override getScene()
    public Scene getGameScene() {
        return scene;
    }

    private void initializeGame() {
        createGameBoard();
        addRandomTile();
        addRandomTile();
        setupKeyboardControls();
        createScoreDisplay();
    }

    private void createGameBoard() {
        double startX = (GameConstants.WINDOW_WIDTH - (4 * CELL_SIZE + 3 * CELL_SPACING)) / 2;
        double startY = (GameConstants.WINDOW_HEIGHT - (4 * CELL_SIZE + 3 * CELL_SPACING)) / 2;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                double x = startX + col * (CELL_SIZE + CELL_SPACING);
                double y = startY + row * (CELL_SIZE + CELL_SPACING);
                cells[row][col] = new Cell(x, y, CELL_SIZE, this);
            }
        }
    }

    private void createScoreDisplay() {
        Label scoreLabel = new Label("SCORE: 0");
        scoreLabel.setFont(Font.font(24));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setLayoutX(650);
        scoreLabel.setLayoutY(50);
        this.getChildren().add(scoreLabel);
    }

    private void setupKeyboardControls() {
        this.setOnKeyPressed(event -> {
            if (gameOver) return;
            KeyCode code = event.getCode();
            boolean moved = false;

            if (code == KeyCode.UP) {
                moved = moveUp();
            } else if (code == KeyCode.DOWN) {
                moved = moveDown();
            } else if (code == KeyCode.LEFT) {
                moved = moveLeft();
            } else if (code == KeyCode.RIGHT) {
                moved = moveRight();
            }

            if (moved) {
                updateScoreDisplay();
                addRandomTile();
                checkGameStatus();
            }
        });
    }

    public void updateBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col].setNumber(board[row][col]);
            }
        }
    }

    public void updateScore(long score) {
        this.score = score;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Label) {
                Label label = (Label) this.getChildren().get(i);
                if (label.getText().startsWith("SCORE:")) {
                    label.setText("SCORE: " + score);
                    break;
                }
            }
        }
    }

    public void updateGameState(boolean isGameOver) {
        this.gameOver = isGameOver;
    }

    private boolean moveUp() {
        boolean moved = false;
        for (int col = 0; col < GRID_SIZE; col++) {
            for (int row = 1; row < GRID_SIZE; row++) {
                if (cells[row][col].getNumber() != 0) {
                    int newPos = findNewPositionUp(row, col);
                    if (newPos != row) {
                        if (cells[newPos][col].getNumber() == 0) {
                            cells[newPos][col].setNumber(cells[row][col].getNumber());
                            cells[row][col].setNumber(0);
                            moved = true;
                        } else if (cells[newPos][col].getNumber() == cells[row][col].getNumber()) {
                            cells[newPos][col].setNumber(cells[row][col].getNumber() * 2);
                            cells[row][col].setNumber(0);
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
        while (newPos > 0 && cells[newPos - 1][col].getNumber() == 0) {
            newPos--;
        }
        return newPos;
    }

    private boolean moveDown() {
        boolean moved = false;
        for (int col = 0; col < GRID_SIZE; col++) {
            for (int row = GRID_SIZE - 2; row >= 0; row--) {
                if (cells[row][col].getNumber() != 0) {
                    int newPos = findNewPositionDown(row, col);
                    if (newPos != row) {
                        if (cells[newPos][col].getNumber() == 0) {
                            cells[newPos][col].setNumber(cells[row][col].getNumber());
                            cells[row][col].setNumber(0);
                            moved = true;
                        } else if (cells[newPos][col].getNumber() == cells[row][col].getNumber()) {
                            cells[newPos][col].setNumber(cells[row][col].getNumber() * 2);
                            cells[row][col].setNumber(0);
                            moved = true;
                        }
                    }
                }
            }
        }
        return moved;
    }

    private int findNewPositionDown(int row, int col) {
        int newPos = row;
        while (newPos < GRID_SIZE - 1 && cells[newPos + 1][col].getNumber() == 0) {
            newPos++;
        }
        return newPos;
    }

    private boolean moveLeft() {
        boolean moved = false;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 1; col < GRID_SIZE; col++) {
                if (cells[row][col].getNumber() != 0) {
                    int newPos = findNewPositionLeft(row, col);
                    if (newPos != col) {
                        if (cells[row][newPos].getNumber() == 0) {
                            cells[row][newPos].setNumber(cells[row][col].getNumber());
                            cells[row][col].setNumber(0);
                            moved = true;
                        } else if (cells[row][newPos].getNumber() == cells[row][col].getNumber()) {
                            cells[row][newPos].setNumber(cells[row][col].getNumber() * 2);
                            cells[row][col].setNumber(0);
                            moved = true;
                        }
                    }
                }
            }
        }
        return moved;
    }

    private int findNewPositionLeft(int row, int col) {
        int newPos = col;
        while (newPos > 0 && cells[row][newPos - 1].getNumber() == 0) {
            newPos--;
        }
        return newPos;
    }

    private boolean moveRight() {
        boolean moved = false;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = GRID_SIZE - 2; col >= 0; col--) {
                if (cells[row][col].getNumber() != 0) {
                    int newPos = findNewPositionRight(row, col);
                    if (newPos != col) {
                        if (cells[row][newPos].getNumber() == 0) {
                            cells[row][newPos].setNumber(cells[row][col].getNumber());
                            cells[row][col].setNumber(0);
                            moved = true;
                        } else if (cells[row][newPos].getNumber() == cells[row][col].getNumber()) {
                            cells[row][newPos].setNumber(cells[row][col].getNumber() * 2);
                            cells[row][col].setNumber(0);
                            moved = true;
                        }
                    }
                }
            }
        }
        return moved;
    }

    private int findNewPositionRight(int row, int col) {
        int newPos = col;
        while (newPos < GRID_SIZE - 1 && cells[row][newPos + 1].getNumber() == 0) {
            newPos++;
        }
        return newPos;
    }

    private void addRandomTile() {
        List<int[]> emptyCells = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (cells[i][j].getNumber() == 0) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }
        if (!emptyCells.isEmpty()) {
            Random random = new Random();
            int[] position = emptyCells.get(random.nextInt(emptyCells.size()));
            cells[position[0]][position[1]].setNumber(Math.random() < 0.9 ? 2 : 4);
        }
    }

    private void updateScoreDisplay() {
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Label) {
                Label label = (Label) this.getChildren().get(i);
                if (label.getText().startsWith("SCORE:")) {
                    label.setText("SCORE: " + score);
                    break;
                }
            }
        }
    }

    private void checkGameStatus() {
        if (hasWon()) {
            System.out.println("You won!");
            gameOver = true;
        } else if (!hasMovesLeft()) {
            System.out.println("Game over!");
            gameOver = true;
        }
    }

    private boolean hasWon() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (cells[row][col].getNumber() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasMovesLeft() {
        // Check if any empty cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (cells[row][col].getNumber() == 0) {
                    return true;
                }
            }
        }
        // Check for possible merges
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = cells[row][col].getNumber();
                // Check right
                if (col < GRID_SIZE - 1 && cells[row][col + 1].getNumber() == value) {
                    return true;
                }
                // Check down
                if (row < GRID_SIZE - 1 && cells[row + 1][col].getNumber() == value) {
                    return true;
                }
            }
        }
        return false;
    }
}