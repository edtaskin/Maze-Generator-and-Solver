module com.example.mazesolver {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mazegeneratorandsolver to javafx.fxml;
    exports com.example.mazegeneratorandsolver;
}