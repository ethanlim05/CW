package com.example.demo;


import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class SceneManager {
    private Stage primaryStage;
    private Scene mainMenuScene;
    private Scene accountScene;
    private Scene leaderboardScene;
    private Scene gameScene;
    private Main mainApp;  // Reference to Main app

    public SceneManager(Stage primaryStage, Main mainApp) {
        this.primaryStage = primaryStage;
        this.mainApp = mainApp;
        setupDefaultScenes();
    }

    public void setupDefaultScenes() {
        // Create scenes with proper content
        mainMenuScene = createSceneWithContent("Main Menu");
        accountScene = createSceneWithContent("Account");
        leaderboardScene = createSceneWithContent("Leaderboard");
        gameScene = createSceneWithContent("Game");
    }

    private Scene createSceneWithContent(String name) {
        Group root = new Group();
        // Background
        Rectangle background = new Rectangle(900, 900);
        background.setFill(Color.rgb(189, 177, 92));
        root.getChildren().add(background);
        // Title
        Text title = new Text(name);
        title.setFont(Font.font(48));
        title.setFill(Color.WHITE);
        title.setX(350);
        title.setY(100);
        root.getChildren().add(title);
        return new Scene(root, 900, 900);
    }

    public void showMainMenu() {
        System.out.println("Showing main menu...");
        MainMenuView mainMenuView = new MainMenuView(this);
        primaryStage.setScene(new Scene(mainMenuView.getRoot(), 900, 900));
        System.out.println("Main menu shown");
    }

    public void showAccount() {
        System.out.println("Switching to account scene");
        AccountView accountView = new AccountView(this, Account.getInstance());
        primaryStage.setScene(new Scene(accountView.getRoot(), 900, 900));
        System.out.println("Account scene shown");
    }

    public void showLeaderboard() {
        System.out.println("Switching to leaderboard scene");
        LeaderboardView leaderboardView = new LeaderboardView(this);
        primaryStage.setScene(new Scene(leaderboardView.getRoot(), 900, 900));
        System.out.println("Leaderboard scene shown");
    }

    public void showGame() {
        mainApp.showGame();  // Use the Main app's showGame method
    }
}