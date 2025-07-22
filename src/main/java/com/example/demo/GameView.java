package com.example.demo;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView extends Pane {
    private static final int GRID_SIZE = 4;
    private static final int CELL_SIZE = 100;
    private static final int CELL_SPACING = 10;

    private final Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
    private long score = 0;
    private boolean gameOver = false;
    private GameModel gameModel;
    private Label scoreLabel;

    public GameView() {
        setPrefSize(Main.getWindowWidth(), Main.getWindowHeight());
        initializeGame();
    }

    private void initializeGame() {
        gameModel = new GameModel();
        createGameBoard();
        createScoreDisplay();

        gameModel.addRandomTile();
        gameModel.addRandomTile();

        updateBoard();
        updateScoreDisplay();
        System.out.println("GameView initialized");
    }

    private void createGameBoard() {
        double startX = (Main.getWindowWidth() - (GRID_SIZE * CELL_SIZE + (GRID_SIZE - 1) * CELL_SPACING)) / 2;
        double startY = (Main.getWindowHeight() - (GRID_SIZE * CELL_SIZE + (GRID_SIZE - 1) * CELL_SPACING)) / 2;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                double x = startX + col * (CELL_SIZE + CELL_SPACING);
                double y = startY + row * (CELL_SIZE + CELL_SPACING);
                cells[row][col] = new Cell(x, y, CELL_SIZE, this);
            }
        }
    }

    private void createScoreDisplay() {
        scoreLabel = new Label("SCORE: 0");
        scoreLabel.setFont(Font.font(24));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setLayoutX(650);
        scoreLabel.setLayoutY(50);
        this.getChildren().add(scoreLabel);
    }

    public void updateBoard() {
        int[][] board = gameModel.getBoard();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col].setNumber(board[row][col]);
            }
        }
    }

    public void updateScoreDisplay() {
        score = gameModel.getScore();
        scoreLabel.setText("SCORE: " + score);
    }

    public boolean moveUp() {
        return gameModel.moveUp();
    }

    public boolean moveDown() {
        return gameModel.moveDown();
    }

    public boolean moveLeft() {
        return gameModel.moveLeft();
    }

    public boolean moveRight() {
        return gameModel.moveRight();
    }

    public void addRandomTile() {
        gameModel.addRandomTile();
    }

    public void checkGameStatus() {
        if (gameModel.hasWon()) {
            System.out.println("You won!");
            gameOver = true;
        } else if (!gameModel.hasMovesLeft()) {
            System.out.println("Game over!");
            gameOver = true;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
