package com.example.demo.view;

import com.example.demo.controller.SceneManager;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MainMenuView {
    private final Group root = new Group();
    private final SceneManager sceneManager;

    public MainMenuView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        setupMainMenu();
    }

    public Group getRoot() {
        return root;
    }

    private void setupMainMenu() {
        System.out.println("=== Setting up Main Menu ===");
        // Clear existing children to ensure fresh UI
        root.getChildren().clear();

        // Background
        System.out.println("Creating background...");
        Rectangle background = new Rectangle(900, 900);
        background.setFill(Color.rgb(189, 177, 92));
        root.getChildren().add(background);
        System.out.println("Background added. Children count: " + root.getChildren().size());

        // Title
        System.out.println("Creating title...");
        Text title = new Text("2048 GAME");
        title.setFont(Font.font(60));
        title.setFill(Color.WHITE);
        title.setX(300);
        title.setY(150);
        root.getChildren().add(title);
        System.out.println("Title added. Children count: " + root.getChildren().size());

        // Create buttons
        System.out.println("Creating buttons...");
        Button playButton = createButton("PLAY", 300);
        Button accountButton = createButton("ACCOUNT", 400);
        Button leaderboardButton = createButton("LEADERBOARD", 500);
        Button quitButton = createButton("QUIT", 600);
        System.out.println("Buttons created. Children count: " + root.getChildren().size());

        // Add button event handlers
        setupButtonActions(playButton, accountButton, leaderboardButton, quitButton);

        // Add buttons to root
        root.getChildren().addAll(playButton, accountButton, leaderboardButton, quitButton);
        System.out.println("All buttons added. Final children count: " + root.getChildren().size());
    }

    private Button createButton(String text, double y) {
        Button button = new Button(text);
        button.setPrefSize(200, 50);
        button.setFont(Font.font(20));
        button.setStyle("-fx-background-color: #8f7a66; -fx-text-fill: white;");
        button.setLayoutX(350); // Fixed x position for all buttons
        button.setLayoutY(y);

        // Add hover effect
        button.setOnMouseEntered(event ->
                button.setStyle("-fx-background-color: #a98467; -fx-text-fill: white;")
        );

        button.setOnMouseExited(event ->
                button.setStyle("-fx-background-color: #8f7a66; -fx-text-fill: white;")
        );

        return button;
    }

    private void setupButtonActions(Button playButton, Button accountButton, Button leaderboardButton, Button quitButton) {
        playButton.setOnAction(event -> {
            System.out.println("Play button clicked");
            sceneManager.showGame();
        });

        accountButton.setOnAction(event -> {
            System.out.println("Account button clicked");
            sceneManager.showAccount();
        });

        leaderboardButton.setOnAction(event -> {
            System.out.println("Leaderboard button clicked");
            sceneManager.showLeaderboard();
        });

        quitButton.setOnAction(event -> {
            System.out.println("Quit button clicked");
            System.exit(0);
        });
    }
}