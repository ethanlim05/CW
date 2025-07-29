package com.example.demo;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameView extends Pane {
    private static final int GRID_SIZE = 4;
    private static final int CELL_SIZE = 150;
    private static final int CELL_SPACING = 15;
    private final Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
    private long score = 0;
    private boolean gameOver = false;
    private GameModel gameModel;
    private Label scoreLabel;
    private Button backButton;
    private SceneManager sceneManager;

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
        createBackButton();
        gameModel.addRandomTile();
        gameModel.addRandomTile();
        updateBoard();
        updateScoreDisplay();
        System.out.println("GameView initialized");
    }

    private void createGameBoard() {
        double startX = (originalWidth - (GRID_SIZE * originalCellSize + (GRID_SIZE - 1) * originalCellSpacing)) / 2;
        double startY = (originalHeight - (GRID_SIZE * originalCellSize + (GRID_SIZE - 1) * originalCellSpacing)) / 2;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                double x = startX + col * (originalCellSize + originalCellSpacing);
                double y = startY + row * (originalCellSize + originalCellSpacing);
                cells[row][col] = new Cell(x, y, originalCellSize, this);
            }
        }
    }

    private void createScoreDisplay() {
        scoreLabel = new Label("SCORE: 0");
        scoreLabel.setFont(Font.font(30));
        scoreLabel.setTextFill(Color.BLACK);
        scoreLabel.setLayoutX(originalWidth - 250);
        scoreLabel.setLayoutY(50);
        this.getChildren().add(scoreLabel);
    }

    private void createBackButton() {
        backButton = new Button("Back to Menu");
        backButton.setStyle("-fx-background-color: #8f7a66; -fx-text-fill: white;");
        backButton.setLayoutX((originalWidth - 200) / 2);
        // Moved button lower - changed from originalHeight - 100 to originalHeight - 50
        backButton.setLayoutY(originalHeight - 50);
        backButton.setPrefSize(200, 60);
        backButton.setFont(Font.font(22));
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
        System.out.println("Back button added. Children count: " + this.getChildren().size());
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
        Text gameOverText = new Text(message);
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
        // Calculate margins (5% of screen size)
        double marginX = screenWidth * 0.05;
        double marginY = screenHeight * 0.05;

        // Calculate available space
        double availableWidth = screenWidth - 2 * marginX;
        double availableHeight = screenHeight - 2 * marginY;

        // Calculate maximum cell size that fits in available space
        // We want to use 80% of available height for the game board
        double boardHeight = availableHeight * 0.8;
        double cellSize = boardHeight / GRID_SIZE;

        // Calculate spacing (10% of cell size)
        double cellSpacing = cellSize * 0.1;

        // Calculate total board width
        double totalBoardWidth = GRID_SIZE * cellSize + (GRID_SIZE - 1) * cellSpacing;

        // Center the board
        double startX = (screenWidth - totalBoardWidth) / 2;
        double startY = marginY + (availableHeight - boardHeight) / 2;

        // Update all cells
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                double x = startX + col * (cellSize + cellSpacing);
                double y = startY + row * (cellSize + cellSpacing);
                cells[row][col].resizeForFullscreen(x, y, cellSize);
            }
        }

        // Calculate UI scale factor based on screen size
        double uiScale = Math.min(screenWidth / originalWidth, screenHeight / originalHeight) * 0.9;

        // Adjust UI elements
        double uiFontSize = 30 * uiScale;
        scoreLabel.setFont(Font.font(uiFontSize));
        scoreLabel.setLayoutX(screenWidth - 250 * uiScale);
        scoreLabel.setLayoutY(50 * uiScale);

        backButton.setFont(Font.font(22 * uiScale));
        backButton.setLayoutX((screenWidth - 200 * uiScale) / 2);
        // Adjusted button position for fullscreen mode - moved lower
        backButton.setLayoutY(screenHeight - 50 * uiScale);
        backButton.setPrefSize(200 * uiScale, 60 * uiScale);
    }

    // New method to reset to original size
    public void resetToOriginalSize() {
        // Reset cell sizes
        double startX = (originalWidth - (GRID_SIZE * originalCellSize + (GRID_SIZE - 1) * originalCellSpacing)) / 2;
        double startY = (originalHeight - (GRID_SIZE * originalCellSize + (GRID_SIZE - 1) * originalCellSpacing)) / 2;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                double x = startX + col * (originalCellSize + originalCellSpacing);
                double y = startY + row * (originalCellSize + originalCellSpacing);
                cells[row][col].resizeForFullscreen(x, y, originalCellSize);
            }
        }

        // Reset UI elements
        scoreLabel.setFont(Font.font(30));
        scoreLabel.setLayoutX(originalWidth - 250);
        scoreLabel.setLayoutY(50);

        backButton.setFont(Font.font(22));
        backButton.setLayoutX((originalWidth - 200) / 2);
        // Reset button position to the new lower position
        backButton.setLayoutY(originalHeight - 50);
        backButton.setPrefSize(200, 60);
    }
}