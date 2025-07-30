package com.example.demo.view;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.Optional;

public class EndGame {
    private static EndGame singleInstance = null;

    private EndGame() {
    }

    public static EndGame getInstance() {
        if (singleInstance == null)
            singleInstance = new EndGame();
        return singleInstance;
    }

    public void endGameShow(Group root, long score) {
        Text gameOverText = new Text("GAME OVER");
        gameOverText.relocate(250, 250);
        gameOverText.setFont(Font.font(80));
        root.getChildren().add(gameOverText);

        Text scoreText = new Text(score + "");
        scoreText.setFill(Color.BLACK);
        scoreText.relocate(250, 600);
        scoreText.setFont(Font.font(80));
        root.getChildren().add(scoreText);

        Button quitButton = new Button("QUIT");
        quitButton.setPrefSize(100, 30);
        quitButton.setTextFill(Color.PINK);
        root.getChildren().add(quitButton);
        quitButton.relocate(100, 800);

        quitButton.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Quit Dialog");
            alert.setHeaderText("Quit from this page");
            alert.setContentText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            result.ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    root.getChildren().clear();
                }
            });
        });
    }
}