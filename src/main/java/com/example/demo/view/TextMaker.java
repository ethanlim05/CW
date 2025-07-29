package com.example.demo.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextMaker {
    private static final TextMaker INSTANCE = new TextMaker();
    private static final double BASE_FONT_SIZE = 24;

    private TextMaker() {}

    public static TextMaker getInstance() {
        return INSTANCE;
    }

    public Text createText(String content, double x, double y, Group root) {
        Text text = new Text(content);
        text.setFont(Font.font(BASE_FONT_SIZE));
        text.setFill(Color.WHITE);
        text.setX(x);
        text.setY(y);
        root.getChildren().add(text);
        return text;
    }

    public void updateText(Text text, String content, double x, double y) {
        text.setText(content);
        text.setX(x);
        text.setY(y);
    }
}