module com.egotec.pwgengui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens com.egotec.pwgengui to javafx.fxml;
    exports com.egotec.pwgengui;
}