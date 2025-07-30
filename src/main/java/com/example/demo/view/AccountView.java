package com.example.demo.view;

import com.example.demo.controller.SceneManager;
import com.example.demo.model.Account;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class AccountView {
    private final Group root;
    private final SceneManager sceneManager;
    private final Account account;

    public AccountView(SceneManager sceneManager, Account account) {
        System.out.println("Creating AccountView...");
        this.sceneManager = sceneManager;
        this.account = account;
        this.root = new Group();
        System.out.println("AccountView root created");
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

        // Username field with current username
        TextField usernameField = new TextField(account.getUserName());
        usernameField.setFont(Font.font(24));
        usernameField.setLayoutX(300);
        usernameField.setLayoutY(200);
        usernameField.setPrefWidth(300);
        usernameField.setPromptText("Enter new username");
        root.getChildren().add(usernameField);
        System.out.println("Username field added. Children count: " + root.getChildren().size());

        // Score display
        Label scoreLabel = new Label("High Score: " + account.getScore());
        scoreLabel.setFont(Font.font(24));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setLayoutX(300);
        scoreLabel.setLayoutY(250);
        root.getChildren().add(scoreLabel);
        System.out.println("Score added. Children count: " + root.getChildren().size());

        // Error label for displaying validation messages
        Label errorLabel = new Label("");
        errorLabel.setFont(Font.font(18));
        errorLabel.setTextFill(Color.RED);
        errorLabel.setLayoutX(300);
        errorLabel.setLayoutY(300);
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(300);
        root.getChildren().add(errorLabel);
        System.out.println("Error label added. Children count: " + root.getChildren().size());

        // Save button
        Button saveButton = createSaveButton(usernameField, scoreLabel, errorLabel);
        root.getChildren().add(saveButton);
        System.out.println("Save button added. Children count: " + root.getChildren().size());

        // Back button
        Button backButton = createBackButton();
        root.getChildren().add(backButton);
        System.out.println("Back button added. Final children count: " + root.getChildren().size());
    }

    private Button createSaveButton(TextField usernameField, Label scoreLabel, Label errorLabel) {
        Button saveButton = new Button("Save Username");
        saveButton.setStyle("-fx-background-color: #8f7a66; -fx-text-fill: white;");
        saveButton.setLayoutX(350);
        saveButton.setLayoutY(400);  // Moved from 300 to 400 to be below error label
        saveButton.setPrefSize(200, 50);
        saveButton.setOnAction(event -> {
            System.out.println("Save button clicked");
            try {
                String newUsername = usernameField.getText().trim();

                // Check if username already exists
                if (Account.usernameExists(newUsername)) {
                    errorLabel.setText("Username already exists in leaderboard. Please choose a different username.");
                    return;
                }

                if (!newUsername.isEmpty()) {
                    account.setUsername(newUsername);
                    scoreLabel.setText("High Score: " + account.getScore());
                    Account.saveScores();  // Save to file
                    errorLabel.setText("");  // Clear error message
                    showSuccessAlert();
                } else {
                    errorLabel.setText("Username cannot be empty!");
                }
            } catch (IllegalArgumentException e) {
                errorLabel.setText(e.getMessage());
            }
        });
        return saveButton;
    }

    private Button createBackButton() {
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
                // Log the exception with more context
                System.err.println("Exception details: " + e.getClass().getName() + ": " + e.getMessage());
            }
        });
        return backButton;
    }

    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Username updated successfully!");
        alert.showAndWait();
    }
}