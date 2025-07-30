package com.example.demo.view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreDisplay {
    public ScoreDisplay(Group root) {
        Label scoreLabel = new Label("Score: 0");
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font(30));
        scoreLabel.relocate(750, 100);
        root.getChildren().add(scoreLabel);
    }
}