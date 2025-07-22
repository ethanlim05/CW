package com.example.demo;


import javafx.application.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class Main extends Application {
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 900;
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.rgb(189, 177, 92);
    private Stage primaryStage;
    private SceneManager sceneManager;
    private GameView gameView;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("2048 Game - Resit Coursework");
        try {
            initializeApplication();
            sceneManager.showMainMenu();
            this.primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeApplication() {
        this.sceneManager = new SceneManager(primaryStage, this);
        this.sceneManager.setupDefaultScenes();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static int getWindowWidth() {
        return WINDOW_WIDTH;
    }

    public static int getWindowHeight() {
        return WINDOW_HEIGHT;
    }

    public static Color getDefaultBackgroundColor() {
        return DEFAULT_BACKGROUND_COLOR;
    }

    // Add this method to provide access to the showGame functionality
    public void showGame() {
        System.out.println("Switching to game scene");
        gameView = new GameView();
        Scene gameScene = new Scene(gameView, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Set up key event handlers
        gameScene.setOnKeyPressed(event -> {
            System.out.println("Key Pressed: " + event.getCode());
            boolean moved = false;
            switch (event.getCode()) {
                case UP -> moved = gameView.moveUp();
                case DOWN -> moved = gameView.moveDown();
                case LEFT -> moved = gameView.moveLeft();
                case RIGHT -> moved = gameView.moveRight();
            }
            if (moved) {
                gameView.updateBoard();
                gameView.updateScoreDisplay();
                gameView.addRandomTile();
                gameView.checkGameStatus();
            }
        });

        // Force focus on the scene's root node
        Platform.runLater(() -> {
            gameScene.getRoot().requestFocus();
            System.out.println("Root requested focus");
        });

        // Debug click event to re-request focus manually if needed
        gameScene.setOnMouseClicked(e -> {
            gameScene.getRoot().requestFocus();
            System.out.println("Mouse clicked, focus requested again");
        });

        primaryStage.setScene(gameScene);
        primaryStage.show();
        System.out.println("Game scene shown");
    }
}