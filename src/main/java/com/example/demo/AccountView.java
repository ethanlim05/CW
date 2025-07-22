package com.example.demo;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AccountView {
    private Group root;
    private final SceneManager sceneManager;
    private final Account account;

    public AccountView(SceneManager sceneManager, Account account) {
        System.out.println("Creating AccountView...");
        this.sceneManager = sceneManager;
        this.account = account;
        this.root = new Group();
        System.out.println("AccountView root created: " + (root != null ? "NOT NULL" : "NULL"));
        setupAccountUI();
    }

    public Group getRoot() {
        return root;
    }

    private void setupAccountUI() {
        System.out.println("Setting up Account UI...");

        // Clear existing children to ensure fresh UI
        root.getChildren().clear();

        // Background
        javafx.scene.shape.Rectangle background = new javafx.scene.shape.Rectangle(900, 900);
        background.setFill(Color.rgb(150, 20, 100, 0.2));
        root.getChildren().add(background);
        System.out.println("Background added. Children count: " + root.getChildren().size());

        // Title
        Text title = new Text("ACCOUNT");
        title.setFont(Font.font(48));
        title.setFill(Color.WHITE);
        title.setX(350);
        title.setY(100);
        root.getChildren().add(title);
        System.out.println("Title added. Children count: " + root.getChildren().size());

        // Username display
        Label usernameLabel = new Label("Username: " + account.getUserName());
        usernameLabel.setFont(Font.font(24));
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setLayoutX(300);
        usernameLabel.setLayoutY(200);
        root.getChildren().add(usernameLabel);
        System.out.println("Username added. Children count: " + root.getChildren().size());

        // Score display
        Label scoreLabel = new Label("High Score: " + account.getScore());
        scoreLabel.setFont(Font.font(24));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setLayoutX(300);
        scoreLabel.setLayoutY(250);
        root.getChildren().add(scoreLabel);
        System.out.println("Score added. Children count: " + root.getChildren().size());

        // Back button - FIXED with proper error handling
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
