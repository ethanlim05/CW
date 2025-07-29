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
    private static final int CELL_SIZE = 100;
    private static final int CELL_SPACING = 10;
    private final Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
    private long score = 0;
    private boolean gameOver = false;
    private GameModel gameModel;
    private Label scoreLabel;
    private Button backButton;
    private Button topBackButton;
    private SceneManager sceneManager;

    public GameView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        setPrefSize(Main.getWindowWidth(), Main.getWindowHeight());
        initializeGame();
    }

    private void initializeGame() {
        gameModel = new GameModel();
        createGameBoard();
        createScoreDisplay();
        createBackButton();
        createTopBackButton();
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
        scoreLabel.setTextFill(Color.BLACK);
        scoreLabel.setLayoutX(650);
        scoreLabel.setLayoutY(50);
        this.getChildren().add(scoreLabel);
    }

    private void createBackButton() {
        backButton = new Button("Back to Menu");
        backButton.setStyle("-fx-background-color: #8f7a66; -fx-text-fill: white;");
        backButton.setLayoutX(350);
        backButton.setLayoutY(800);
        backButton.setPrefSize(200, 50);
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

    private void createTopBackButton() {
        topBackButton = new Button("Back to Menu");
        topBackButton.setStyle("-fx-background-color: #8f7a66; -fx-text-fill: white;");
        topBackButton.setLayoutX(20);
        topBackButton.setLayoutY(20);
        topBackButton.setPrefSize(150, 40);
        topBackButton.setOnAction(event -> {
            System.out.println("Top back button clicked");
            try {
                sceneManager.showMainMenu();
            } catch (Exception e) {
                System.err.println("Error switching to main menu: " + e.getMessage());
                e.printStackTrace();
            }
        });
        this.getChildren().add(topBackButton);
        System.out.println("Top back button added. Children count: " + this.getChildren().size());
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
                Main.getWindowWidth(), Main.getWindowHeight(), Color.rgb(0, 0, 0, 0.7));
        overlay.setMouseTransparent(true);

        // Create game over text
        Text gameOverText = new Text(message);
        gameOverText.setFont(Font.font(60));
        gameOverText.setFill(Color.WHITE);
        gameOverText.setX(Main.getWindowWidth() / 2 - 150);
        gameOverText.setY(Main.getWindowHeight() / 2 - 50);

        // Create back to menu button
        Button menuButton = new Button("Back to Menu");
        menuButton.setStyle("-fx-background-color: #8f7a66; -fx-text-fill: white;");
        menuButton.setPrefSize(200, 50);
        menuButton.setLayoutX(Main.getWindowWidth() / 2 - 100);
        menuButton.setLayoutY(Main.getWindowHeight() / 2 + 50);
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
}