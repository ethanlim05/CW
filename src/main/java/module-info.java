module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base; // Add this for basic Java classes
    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}