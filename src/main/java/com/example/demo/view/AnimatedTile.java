package com.example.demo.view;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class AnimatedTile extends Group {
    private final Rectangle rectangle;
    private final Text text;
    private int number = 0;
    private double size;

    public AnimatedTile(double size) {
        this.size = size;

        // Create rectangle
        rectangle = new Rectangle(0, 0, size, size);
        rectangle.setFill(Color.rgb(205, 193, 180));
        rectangle.setArcWidth(15);
        rectangle.setArcHeight(15);

        // Create text - positioned at center
        text = new Text("0");
        text.setFont(Font.font(size * 0.45)); // Increased font size ratio
        text.setFill(Color.WHITE);
        text.setX(size / 2);
        text.setY(size / 2);

        // Add to group
        getChildren().addAll(rectangle, text);
    }

    public void setNumber(int number) {
        this.number = number;
        updateDisplay();
    }

    public int getNumber() {
        return number;
    }

    private void updateDisplay() {
        // Update text
        if (number == 0) {
            text.setText("");
            rectangle.setFill(Color.rgb(205, 193, 180));
        } else {
            text.setText(String.valueOf(number));

            // Adjust font size for larger numbers
            double fontSize = size * 0.45; // Increased base ratio
            if (number >= 1000) {
                fontSize *= 0.8;
            }
            text.setFont(Font.font(fontSize));

            // Set color based on number
            rectangle.setFill(getColorForNumber(number));
        }

        // Keep text centered
        centerText();
    }

    private void centerText() {
        // Calculate text position to center it
        double textWidth = text.getLayoutBounds().getWidth();
        double textHeight = text.getLayoutBounds().getHeight();

        // Center horizontally
        text.setX((size - textWidth) / 2);

        // Center vertically (adjust for text baseline)
        text.setY((size + textHeight * 0.7) / 2);
    }

    private Color getColorForNumber(int number) {
        return switch (number) {
            case 0 -> Color.rgb(205, 193, 180);
            case 2 -> Color.rgb(238, 228, 218);
            case 4 -> Color.rgb(237, 224, 200);
            case 8 -> Color.rgb(242, 177, 121);
            case 16 -> Color.rgb(245, 149, 99);
            case 32 -> Color.rgb(246, 124, 95);
            case 64 -> Color.rgb(246, 94, 59);
            case 128 -> Color.rgb(237, 207, 114);
            case 256 -> Color.rgb(237, 204, 97);
            case 512 -> Color.rgb(237, 200, 80);
            case 1024 -> Color.rgb(237, 197, 63);
            case 2048 -> Color.rgb(237, 194, 46);
            default -> Color.rgb(60, 58, 50);
        };
    }

    public void animateNewTile() {
        // Simple scale animation for new tiles
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), this);
        scaleTransition.setFromX(0);
        scaleTransition.setFromY(0);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
    }

    public void resize(double size) {
        this.size = size;
        rectangle.setWidth(size);
        rectangle.setHeight(size);
        updateDisplay();
    }
}