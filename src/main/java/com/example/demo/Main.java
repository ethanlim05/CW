package com.example.demo;

import javafx.application.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import com.example.demo.controller.SceneManager;
import com.example.demo.view.GameView;
import com.example.demo.model.Account;

public class Main extends Application {
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 900;
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.rgb(189, 177, 92);
    private Stage primaryStage;
    private SceneManager sceneManager;
    private GameView gameView;
    private boolean isFullscreen = false;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("2048 Game - Resit Coursework");
        try {
            initializeApplication();
            Account.loadScores(); // Load saved scores
            sceneManager.showMainMenu();
            this.primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeApplication() {
        this.sceneManager = new SceneManager(primaryStage, this);
        // Removed: this.sceneManager.setupDefaultScenes();
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

    public void showGame() {
        System.out.println("Switching to game scene");
        gameView = new GameView(sceneManager);
        Scene gameScene = new Scene(gameView, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Set up key event handlers
        gameScene.setOnKeyPressed(event -> {
            System.out.println("Key Pressed: " + event.getCode());
            // Handle ESC key to exit fullscreen
            if (event.getCode() == KeyCode.ESCAPE && isFullscreen) {
                exitFullscreen();
                return;
            }
            boolean moved = false;
            switch (event.getCode()) {
                case UP -> moved = gameView.moveUp();
                case DOWN -> moved = gameView.moveDown();
                case LEFT -> moved = gameView.moveLeft();
                case RIGHT -> moved = gameView.moveRight();
            }
            // Note: Animation handling is now inside GameView's move methods
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
        // Enter fullscreen mode
        enterFullscreen();
        System.out.println("Game scene shown in fullscreen mode");
    }

    private void enterFullscreen() {
        isFullscreen = true;
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("Press ESC to exit fullscreen");
        // Adjust game view to fullscreen
        if (gameView != null) {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            gameView.adjustToFullscreen(screenBounds.getWidth(), screenBounds.getHeight());
        }
    }

    private void exitFullscreen() {
        isFullscreen = false;
        primaryStage.setFullScreen(false);
        // Reset game view to original size
        if (gameView != null) {
            gameView.resetToOriginalSize();
        }
    }
}