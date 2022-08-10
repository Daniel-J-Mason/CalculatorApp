module com.dev.calculatorapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.dev.calculatorapp to javafx.fxml;
    exports com.dev.calculatorapp;
}