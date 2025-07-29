package com.example.demo.view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreDisplay {
    private final Label scoreLabel;

    public ScoreDisplay(Group root) {
        scoreLabel = new Label("Score: 0");
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font(30));
        scoreLabel.relocate(750, 100);
        root.getChildren().add(scoreLabel);
    }

    public void updateScore(long score) {
        scoreLabel.setText("Score: " + score);
    }
}
