package com.example.demo;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;  // Add this import for KeyCode
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
            moved = gameView.moveUp();
        } else if (code == KeyCode.DOWN) {
            moved = gameView.moveDown();
        } else if (code == KeyCode.LEFT) {
            moved = gameView.moveLeft();
        } else if (code == KeyCode.RIGHT) {
            moved = gameView.moveRight();
        }

        if (moved) {
            gameView.updateBoard();
            gameView.updateScoreDisplay();
            gameView.addRandomTile();
            gameView.checkGameStatus();
        }
    }
}