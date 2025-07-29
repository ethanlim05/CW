module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base; // Add this for basic Java classes
    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
    exports com.example.demo.model;
    opens com.example.demo.model to javafx.fxml;
    exports com.example.demo.view;
    opens com.example.demo.view to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.controller to javafx.fxml;
}