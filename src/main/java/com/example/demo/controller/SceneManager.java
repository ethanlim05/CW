package com.example.demo.controller;

import com.example.demo.Main;
import com.example.demo.model.Account;
import com.example.demo.view.AccountView;
import com.example.demo.view.LeaderboardView;
import com.example.demo.view.MainMenuView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private final Stage primaryStage;
    private final Main mainApp;

    public SceneManager(Stage primaryStage, Main mainApp) {
        this.primaryStage = primaryStage;
        this.mainApp = mainApp;
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
        mainApp.showGame();
    }
}