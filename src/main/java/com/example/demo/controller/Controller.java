package com.example.demo.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import com.example.demo.model.GameModel;
import com.example.demo.view.GameView;

public class Controller {
    private final GameModel gameModel;
    private final GameView gameView;
    private Scene scene;

    public Controller(GameModel model, GameView view) {
        this.gameModel = model;
        this.gameView = view;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        if (scene != null) {
            scene.setOnKeyPressed(this::handleKeyPress);
        }
    }

    private void handleKeyPress(KeyEvent event) {
        if (gameModel.isGameOver()) return;

        KeyCode code = event.getCode();

        if (code == KeyCode.UP) {
            gameView.moveUp();
        } else if (code == KeyCode.DOWN) {
            gameView.moveDown();
        } else if (code == KeyCode.LEFT) {
            gameView.moveLeft();
        } else if (code == KeyCode.RIGHT) {
            gameView.moveRight();
        }

        // Check game status after any move
        gameView.checkGameStatus();
    }
}