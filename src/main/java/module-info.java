module com.egotec.pwgengui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.json;


    opens com.egotec.pwgengui to javafx.fxml;
    exports com.egotec.pwgengui;
}