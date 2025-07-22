package com.example.demo;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Cell {
    private final Rectangle rectangle;
    private final Text text;
    private final Pane root;  // Changed from Group to Pane
    private int number = 0;

    public Cell(double x, double y, double size, Pane root) {
        this.root = root;

        // Create rectangle
        rectangle = new Rectangle(x, y, size, size);
        rectangle.setFill(getColorForNumber(0));
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);

        // Create text
        text = new Text();
        text.setFont(Font.font(24));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.WHITE);
        text.setX(x + size / 2);
        text.setY(y + size / 2);
        text.setText("0");

        // Add to root
        root.getChildren().addAll(rectangle, text);
    }

    public void setNumber(int number) {
        this.number = number;
        updateDisplay();
    }

    public int getNumber() {
        return number;
    }

    public double getX() {
        return rectangle.getX();
    }

    public double getY() {
        return rectangle.getY();
    }

    public boolean isEmpty() {
        return number == 0;
    }

    public void clear() {
        setNumber(0);
    }

    private void updateDisplay() {
        // Update text
        if (number == 0) {
            text.setText("");
        } else {
            text.setText(String.valueOf(number));
            // Adjust font size for larger numbers
            if (number >= 1000) {
                text.setFont(Font.font(18));
            } else {
                text.setFont(Font.font(24));
            }
        }

        // Center text
        text.setX(getX() + rectangle.getWidth() / 2 - text.getLayoutBounds().getWidth() / 2);
        text.setY(getY() + rectangle.getHeight() / 2 + text.getLayoutBounds().getHeight() / 4);

        // Update color
        rectangle.setFill(getColorForNumber(number));
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
}
