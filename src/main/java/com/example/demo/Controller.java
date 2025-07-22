package com.example.demo;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class Controller {
    private GameModel gameModel;
    private GameView gameView;
    private final SceneManager sceneManager;

    public Controller(GameModel model, GameView view, SceneManager sceneManager) {
        this.gameModel = model;
        this.gameView = view;
        this.sceneManager = sceneManager;
    }

    public void setupEventHandlers(Scene scene) {
        scene.setOnKeyPressed(this::handleKeyPress);
    }

    private void handleKeyPress(KeyEvent event) {
        if (gameModel.isGameOver()) return;
        KeyCode code = event.getCode();
        boolean moved = false;

        if (code == KeyCode.UP) {
            moved = gameModel.moveUp();
        } else if (code == KeyCode.DOWN) {
            moved = gameModel.moveDown();
        } else if (code == KeyCode.LEFT) {
            moved = gameModel.moveLeft();
        } else if (code == KeyCode.RIGHT) {
            moved = gameModel.moveRight();
        }

        if (moved) {
            gameModel.addRandomTile();
            updateView();
            if (gameModel.isGameOver()) {
                sceneManager.showMainMenu();
            }
        }
    }

    private void updateView() {
        gameView.updateBoard();  // Fixed: No parameters needed
        gameView.updateScoreDisplay();  // Fixed: Use correct method name
    }
}