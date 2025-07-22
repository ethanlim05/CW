package com.example.demo;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardView {
    private Group root;
    private final SceneManager sceneManager;

    public LeaderboardView(SceneManager sceneManager) {
        System.out.println("Creating LeaderboardView...");
        this.sceneManager = sceneManager;
        this.root = new Group();
        System.out.println("LeaderboardView root created: " + (root != null ? "NOT NULL" : "NULL"));
        setupLeaderboardUI();
    }

    public Group getRoot() {
        return root;
    }

    private void setupLeaderboardUI() {
        System.out.println("Setting up Leaderboard UI...");

        // Background
        javafx.scene.shape.Rectangle background = new javafx.scene.shape.Rectangle(900, 900);
        background.setFill(Color.rgb(250, 50, 120, 0.3));
        root.getChildren().add(background);
        System.out.println("Background added. Children count: " + root.getChildren().size());

        // Title
        Text title = new Text("LEADERBOARD");
        title.setFont(Font.font(48));
        title.setFill(Color.WHITE);
        title.setX(300);
        title.setY(100);
        root.getChildren().add(title);
        System.out.println("Title added. Children count: " + root.getChildren().size());

        // Sample leaderboard entries
        String[] playerNames = {"Player1", "Player2", "Player3", "Player4", "Player5"};
        long[] scores = {5000, 3500, 2800, 1200, 800};

        // Display leaderboard entries
        for (int i = 0; i < playerNames.length; i++) {
            Label entry = new Label((i + 1) + ". " + playerNames[i] + " - " + scores[i]);
            entry.setFont(Font.font(24));
            entry.setTextFill(Color.WHITE);
            entry.setLayoutX(300);
            entry.setLayoutY(200 + i * 50);
            root.getChildren().add(entry);
            System.out.println("Entry " + (i+1) + " added. Children count: " + root.getChildren().size());
        }

        // Back button - FIXED
        Button backButton = new Button("Back to Menu");
        backButton.setStyle("-fx-background-color: #8f7a66; -fx-text-fill: white;");
        backButton.setLayoutX(350);
        backButton.setLayoutY(700);
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
        root.getChildren().add(backButton);
        System.out.println("Back button added. Final children count: " + root.getChildren().size());
    }
}