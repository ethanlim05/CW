package com.example.demo.view;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import com.example.demo.model.GameModel;
import com.example.demo.model.Account;
import com.example.demo.controller.SceneManager;
import com.example.demo.Main;

import java.util.ArrayList;
import java.util.List;

public class GameView extends Pane {
    private static final int GRID_SIZE = 4;
    // Increased cell size and spacing for larger grid
    private static final int CELL_SIZE = 200;  // Increased from 150
    private static final int CELL_SPACING = 20; // Increased from 15
    private final Pane[][] cells = new Pane[GRID_SIZE][GRID_SIZE];
    private final AnimatedTile[][] tiles = new AnimatedTile[GRID_SIZE][GRID_SIZE];
    private long score = 0;
    private boolean gameOver = false;
    private GameModel gameModel;
    private Label scoreLabel;
    private Button backButton;
    private SceneManager sceneManager;
    private boolean isAnimating = false;
    private Pane gridContainer;

    // Store original dimensions for resetting
    private double originalWidth;
    private double originalHeight;
    private double originalCellSize;
    private double originalCellSpacing;

    public GameView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.originalWidth = Main.getWindowWidth();
        this.originalHeight = Main.getWindowHeight();
        this.originalCellSize = CELL_SIZE;
        this.originalCellSpacing = CELL_SPACING;

        setPrefSize(originalWidth, originalHeight);
        initializeGame();
    }

    private void initializeGame() {
        gameModel = new GameModel();
        createGameBoard();
        createScoreDisplay();
        createBackButton(); // Create back button first so it's behind other elements
        gameModel.addRandomTile();
        gameModel.addRandomTile();
        updateBoard();
        updateScoreDisplay();
        System.out.println("GameView initialized");
    }

    private void createGameBoard() {
        // Calculate grid dimensions with larger cells
        double gridWidth = GRID_SIZE * originalCellSize + (GRID_SIZE - 1) * originalCellSpacing;
        double gridHeight = GRID_SIZE * originalCellSize + (GRID_SIZE - 1) * originalCellSpacing;

        // Add padding to ensure tiles are fully visible
        double padding = originalCellSpacing;
        double extraBottomPadding = originalCellSpacing * 5; // Further increased extra padding at the bottom
        double paddedGridWidth = gridWidth + 2 * padding;
        double paddedGridHeight = gridHeight + padding + extraBottomPadding; // Extra space at bottom

        // Center the grid with more space
        double startX = (originalWidth - paddedGridWidth) / 2;
        // Move grid down a bit to make room for top-left button
        double startY = (originalHeight - paddedGridHeight) / 2 + 30;

        // Create a container for the grid
        gridContainer = new Pane();
        gridContainer.setLayoutX(startX);
        gridContainer.setLayoutY(startY);
        gridContainer.setPrefSize(paddedGridWidth, paddedGridHeight);

        // Add clipping to ensure nothing appears outside the grid
        Rectangle clip = new Rectangle(0, 0, paddedGridWidth, paddedGridHeight);
        gridContainer.setClip(clip);

        this.getChildren().add(gridContainer);

        // Create background for the entire grid (now covers the entire padded area)
        Rectangle gridBackground = new Rectangle(0, 0, paddedGridWidth, paddedGridHeight);
        gridBackground.setFill(Color.rgb(187, 173, 160));
        gridBackground.setArcWidth(20);
        gridBackground.setArcHeight(20);
        gridContainer.getChildren().add(gridBackground);

        // Create cell containers and tiles
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                double x = padding + col * (originalCellSize + originalCellSpacing);
                double y = padding + row * (originalCellSize + originalCellSpacing);

                // Create cell container
                Pane cell = new Pane();
                cell.setLayoutX(x);
                cell.setLayoutY(y);
                cell.setPrefSize(originalCellSize, originalCellSize);

                // Add clipping to each cell to ensure tiles stay within
                Rectangle cellClip = new Rectangle(0, 0, originalCellSize, originalCellSize);
                cell.setClip(cellClip);

                cells[row][col] = cell;
                gridContainer.getChildren().add(cell);

                // Create cell background
                Rectangle cellBg = new Rectangle(0, 0, originalCellSize, originalCellSize);
                cellBg.setFill(Color.rgb(205, 193, 180));
                cellBg.setArcWidth(10);
                cellBg.setArcHeight(10);
                cell.getChildren().add(cellBg);

                // Create animated tile (initially hidden)
                AnimatedTile tile = new AnimatedTile(originalCellSize);
                tile.setVisible(false);
                tiles[row][col] = tile;
                cell.getChildren().add(tile);
            }
        }
    }

    private void createScoreDisplay() {
        scoreLabel = new Label("SCORE: 0");
        scoreLabel.setFont(Font.font(36)); // Increased font size
        scoreLabel.setTextFill(Color.BLACK);
        // Position score at top right
        scoreLabel.setLayoutX(originalWidth - 200);
        scoreLabel.setLayoutY(30);
        this.getChildren().add(scoreLabel);
    }

    private void createBackButton() {
        backButton = new Button("Back to Menu");
        backButton.setStyle("-fx-background-color: #8f7a66; -fx-text-fill: white;");
        // Position at top left
        backButton.setLayoutX(20);
        backButton.setLayoutY(20);
        backButton.setPrefSize(150, 50); // Increased button size
        backButton.setFont(Font.font(18)); // Increased font size
        backButton.setOnAction(event -> {
            System.out.println("Back button clicked");
            try {
                sceneManager.showMainMenu();
            } catch (Exception e) {
                System.err.println("Error switching to main menu: " + e.getMessage());
                e.printStackTrace();
            }
        });
        this.getChildren().add(backButton);
        System.out.println("Back button added at top left");
    }

    public void updateBoard() {
        int[][] board = gameModel.getBoard();

        // Store current positions to detect movements
        List<TilePosition> oldPositions = new ArrayList<>();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (tiles[row][col].isVisible() && tiles[row][col].getNumber() != 0) {
                    oldPositions.add(new TilePosition(row, col, tiles[row][col].getNumber()));
                }
            }
        }

        // Update board with animations
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int value = board[row][col];

                if (value != 0) {
                    // Show and update the tile
                    tiles[row][col].setVisible(true);
                    tiles[row][col].setNumber(value);

                    // Animate new tiles
                    if (!wasTileAtPosition(oldPositions, row, col, value)) {
                        tiles[row][col].animateNewTile();
                    }
                } else {
                    // Hide the tile
                    tiles[row][col].setVisible(false);
                }
            }
        }
    }

    private boolean wasTileAtPosition(List<TilePosition> positions, int row, int col, int value) {
        for (TilePosition pos : positions) {
            if (pos.row == row && pos.col == col && pos.value == value) {
                return true;
            }
        }
        return false;
    }

    private static class TilePosition {
        int row, col, value;

        TilePosition(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }
    }

    public void updateScoreDisplay() {
        score = gameModel.getScore();
        scoreLabel.setText("SCORE: " + score);
    }

    public boolean moveUp() {
        if (isAnimating) return false;

        boolean moved = gameModel.moveUp();
        if (moved) {
            animateMove("UP");
        }
        return moved;
    }

    public boolean moveDown() {
        if (isAnimating) return false;

        boolean moved = gameModel.moveDown();
        if (moved) {
            animateMove("DOWN");
        }
        return moved;
    }

    public boolean moveLeft() {
        if (isAnimating) return false;

        boolean moved = gameModel.moveLeft();
        if (moved) {
            animateMove("LEFT");
        }
        return moved;
    }

    public boolean moveRight() {
        if (isAnimating) return false;

        boolean moved = gameModel.moveRight();
        if (moved) {
            animateMove("RIGHT");
        }
        return moved;
    }

    private void animateMove(String direction) {
        isAnimating = true;

        // Set a timer to mark animation as complete
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(150),
                ae -> {
                    isAnimating = false;
                    updateBoard();
                    updateScoreDisplay();
                    addRandomTile();
                    checkGameStatus();
                }
        ));
        timeline.play();
    }

    public void addRandomTile() {
        gameModel.addRandomTile();
    }

    public void checkGameStatus() {
        if (gameModel.hasWon()) {
            System.out.println("You won!");
            showGameOverScreen("YOU WIN!");
        } else if (!gameModel.hasMovesLeft()) {
            System.out.println("Game over!");
            showGameOverScreen("GAME OVER");
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void showGameOverScreen(String message) {
        // Create a semi-transparent overlay
        Rectangle overlay = new Rectangle(
                getWidth(), getHeight(), Color.rgb(0, 0, 0, 0.7));
        overlay.setMouseTransparent(true);

        // Create game over text
        javafx.scene.text.Text gameOverText = new javafx.scene.text.Text(message);
        gameOverText.setFont(Font.font(80));
        gameOverText.setFill(Color.WHITE);
        gameOverText.setX(getWidth() / 2 - 200);
        gameOverText.setY(getHeight() / 2 - 50);

        // Create back to menu button
        Button menuButton = new Button("Back to Menu");
        menuButton.setStyle("-fx-background-color: #8f7a66; -fx-text-fill: white;");
        menuButton.setPrefSize(250, 70);
        menuButton.setFont(Font.font(26));
        menuButton.setLayoutX(getWidth() / 2 - 125);
        menuButton.setLayoutY(getHeight() / 2 + 50);
        menuButton.setOnAction(event -> {
            System.out.println("Menu button clicked from game over screen");
            try {
                // Find the account with the current username and update its score
                Account account = Account.getInstance();  // Get the current account
                System.out.println("DEBUG - Account found: " + account.getUserName() + " with score: " + account.getScore());
                account.addToScore(score);  // Add the actual game score
                System.out.println("DEBUG - Score after adding: " + account.getScore());
                Account.saveScores();  // Save to file
                System.out.println("Score saved successfully! New score: " + score);
                sceneManager.showMainMenu();
            } catch (Exception e) {
                System.err.println("Error switching to main menu: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // Add elements to the game view
        this.getChildren().addAll(overlay, gameOverText, menuButton);
    }

    // New method to adjust to fullscreen
    public void adjustToFullscreen(double screenWidth, double screenHeight) {
        // Calculate margins (3% of screen size - reduced for more space)
        double marginX = screenWidth * 0.03;
        double marginY = screenHeight * 0.03;

        // Calculate available space
        double availableWidth = screenWidth - 2 * marginX;
        double availableHeight = screenHeight - 2 * marginY;

        // Calculate maximum cell size that fits in available space
        // Use 80% of available height for the game board (increased from 70%)
        double boardHeight = availableHeight * 0.8;
        double cellSize = boardHeight / GRID_SIZE;

        // Calculate spacing (10% of cell size)
        double cellSpacing = cellSize * 0.1;

        // Calculate grid dimensions
        double gridWidth = GRID_SIZE * cellSize + (GRID_SIZE - 1) * cellSpacing;

        // Add padding to ensure tiles are fully visible
        double padding = cellSpacing;
        double extraBottomPadding = cellSpacing * 5; // Further increased extra padding at the bottom
        double paddedGridWidth = gridWidth + 2 * padding;
        double paddedGridHeight = boardHeight + padding + extraBottomPadding; // Extra space at bottom

        // Center the board
        double startX = (screenWidth - paddedGridWidth) / 2;
        // Move grid down a bit to make room for top-left button
        double startY = marginY + (availableHeight - paddedGridHeight) / 2 + 30;

        // Update grid container position and size
        gridContainer.setLayoutX(startX);
        gridContainer.setLayoutY(startY);
        gridContainer.setPrefSize(paddedGridWidth, paddedGridHeight);

        // Update grid container clip
        Rectangle gridClip = new Rectangle(0, 0, paddedGridWidth, paddedGridHeight);
        gridContainer.setClip(gridClip);

        // Update grid background (now covers entire padded area)
        Rectangle gridBackground = (Rectangle) gridContainer.getChildren().get(0);
        gridBackground.setWidth(paddedGridWidth);
        gridBackground.setHeight(paddedGridHeight);

        // Update all cell containers and tiles
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                double x = padding + col * (cellSize + cellSpacing);
                double y = padding + row * (cellSize + cellSpacing);

                // Update cell container
                cells[row][col].setLayoutX(x);
                cells[row][col].setLayoutY(y);
                cells[row][col].setPrefSize(cellSize, cellSize);

                // Update cell clip
                Rectangle cellClip = new Rectangle(0, 0, cellSize, cellSize);
                cells[row][col].setClip(cellClip);

                // Update cell background
                Rectangle cellBg = (Rectangle) cells[row][col].getChildren().get(0);
                cellBg.setWidth(cellSize);
                cellBg.setHeight(cellSize);

                // Update tile
                tiles[row][col].resize(cellSize);
            }
        }

        // Calculate UI scale factor based on screen size
        double uiScale = Math.min(screenWidth / originalWidth, screenHeight / originalHeight) * 0.9;

        // Adjust UI elements
        double uiFontSize = 36 * uiScale; // Increased base font size
        scoreLabel.setFont(Font.font(uiFontSize));
        scoreLabel.setLayoutX(screenWidth - 200 * uiScale);
        scoreLabel.setLayoutY(30 * uiScale);

        // Position back button at top left
        backButton.setFont(Font.font(18 * uiScale));
        backButton.setLayoutX(20 * uiScale);
        backButton.setLayoutY(20 * uiScale);
        backButton.setPrefSize(150 * uiScale, 50 * uiScale);
    }

    // New method to reset to original size
    public void resetToOriginalSize() {
        // Reset tile sizes
        double gridWidth = GRID_SIZE * originalCellSize + (GRID_SIZE - 1) * originalCellSpacing;
        double gridHeight = GRID_SIZE * originalCellSize + (GRID_SIZE - 1) * originalCellSpacing;

        // Add padding to ensure tiles are fully visible
        double padding = originalCellSpacing;
        double extraBottomPadding = originalCellSpacing * 5; // Further increased extra padding at the bottom
        double paddedGridWidth = gridWidth + 2 * padding;
        double paddedGridHeight = gridHeight + padding + extraBottomPadding; // Extra space at bottom

        double startX = (originalWidth - paddedGridWidth) / 2;
        // Move grid down a bit to make room for top-left button
        double startY = (originalHeight - paddedGridHeight) / 2 + 30;

        // Update grid container position and size
        gridContainer.setLayoutX(startX);
        gridContainer.setLayoutY(startY);
        gridContainer.setPrefSize(paddedGridWidth, paddedGridHeight);

        // Update grid container clip
        Rectangle gridClip = new Rectangle(0, 0, paddedGridWidth, paddedGridHeight);
        gridContainer.setClip(gridClip);

        // Update grid background (now covers entire padded area)
        Rectangle gridBackground = (Rectangle) gridContainer.getChildren().get(0);
        gridBackground.setWidth(paddedGridWidth);
        gridBackground.setHeight(paddedGridHeight);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                double x = padding + col * (originalCellSize + originalCellSpacing);
                double y = padding + row * (originalCellSize + originalCellSpacing);

                // Update cell container
                cells[row][col].setLayoutX(x);
                cells[row][col].setLayoutY(y);
                cells[row][col].setPrefSize(originalCellSize, originalCellSize);

                // Update cell clip
                Rectangle cellClip = new Rectangle(0, 0, originalCellSize, originalCellSize);
                cells[row][col].setClip(cellClip);

                // Update cell background
                Rectangle cellBg = (Rectangle) cells[row][col].getChildren().get(0);
                cellBg.setWidth(originalCellSize);
                cellBg.setHeight(originalCellSize);

                // Update tile
                tiles[row][col].resize(originalCellSize);
            }
        }

        // Reset UI elements
        scoreLabel.setFont(Font.font(36));
        scoreLabel.setLayoutX(originalWidth - 200);
        scoreLabel.setLayoutY(30);

        // Position back button at top left
        backButton.setFont(Font.font(18));
        backButton.setLayoutX(20);
        backButton.setLayoutY(20);
        backButton.setPrefSize(150, 50);
    }
}