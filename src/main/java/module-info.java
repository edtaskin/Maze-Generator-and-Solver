module com.example.mazesolver {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.example.mazegeneratorandsolver.maze;
    opens com.example.mazegeneratorandsolver.maze to javafx.fxml;
}