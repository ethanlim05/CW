package com.example.demo.view;


import com.example.demo.controller.SceneManager;
import com.example.demo.model.Account;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Collections;
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
        // Clear existing children to ensure fresh UI
        root.getChildren().clear();

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

        // Load and display scores from Account class
        displayLeaderboardScores();

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

    private void displayLeaderboardScores() {
        // Load all accounts and sort by score (highest first)
        List<Account> accounts = Account.getAllAccounts();

        // DEBUG: Print accounts before sorting
        System.out.println("DEBUG - Accounts before sorting:");
        for (Account account : accounts) {
            System.out.println("  " + account.getUserName() + ": " + account.getScore());
        }

        // Sort accounts by score (highest first)
        Collections.sort(accounts, (a, b) -> Long.compare(b.getScore(), a.getScore()));

        // DEBUG: Print accounts after sorting
        System.out.println("DEBUG - Accounts after sorting:");
        for (Account account : accounts) {
            System.out.println("  " + account.getUserName() + ": " + account.getScore());
        }

        // Display leaderboard entries
        double startY = 200;
        for (int i = 0; i < Math.min(accounts.size(), 10); i++) {  // Show top 10 scores
            Account account = accounts.get(i);
            String entry = (i + 1) + ". " + account.getUserName() + " - " + account.getScore();

            Label entryLabel = new Label(entry);
            entryLabel.setFont(Font.font(24));
            entryLabel.setTextFill(Color.WHITE);
            entryLabel.setLayoutX(300);
            entryLabel.setLayoutY(startY + i * 50);
            root.getChildren().add(entryLabel);
            System.out.println("Entry " + (i+1) + " added. Children count: " + root.getChildren().size());
        }

        // If no scores, show a message
        if (accounts.isEmpty()) {
            Label noScoresLabel = new Label("No scores yet. Play a game to set a record!");
            noScoresLabel.setFont(Font.font(20));
            noScoresLabel.setTextFill(Color.WHITE);
            noScoresLabel.setLayoutX(300);
            noScoresLabel.setLayoutY(250);
            root.getChildren().add(noScoresLabel);
            System.out.println("No scores message added. Children count: " + root.getChildren().size());
        }
    }
}