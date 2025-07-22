package com.example.demo;

import javafx.scene.Group;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.example.demo.GameView;

public class Main extends Application {
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 900;
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.rgb(189, 177, 92);
    private Stage primaryStage;
    private SceneManager sceneManager;

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Application starting...");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("2048 Game - Resit Coursework");
        try {
            initializeApplication();
            showMainMenu();
            // Keep the application running
            this.primaryStage.show();
            System.out.println("Application started successfully");
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeApplication() {
        System.out.println("Initializing application...");
        this.sceneManager = new SceneManager(primaryStage);
        this.sceneManager.setupDefaultScenes();
        System.out.println("Application initialized");
    }

    private void showMainMenu() {
        System.out.println("Showing main menu...");
        MainMenuView mainMenuView = new MainMenuView(sceneManager);
        this.primaryStage.setScene(new Scene(mainMenuView.getRoot(), 900, 900));
        this.primaryStage.show();
        System.out.println("Main menu shown");
    }

    public static void main(String[] args) {
        System.out.println("Launching application...");
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
        GameView gameView = new GameView(); // No parameters needed
        this.primaryStage.setScene(new Scene(gameView, 900, 900));
        this.primaryStage.show();
        System.out.println("Game scene displayed");
    }
}