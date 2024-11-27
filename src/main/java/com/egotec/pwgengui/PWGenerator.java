package com.egotec.pwgengui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;

public class PWGenerator extends Application {

    // Grundlegende Box
    VBox vBoxMain = new VBox(15);
    //Erstellung der Box
    VBox hHeaderBox = new VBox(25);
    VBox hTextBox = new VBox(25);
    HBox hBodyBox = new HBox(25);
    VBox hBodyBoxLinks = new VBox(25);
    VBox hBodyBoxRechts = new VBox(25);
    VBox hSliderBox = new VBox(25);
    HBox hSliderBoxText = new HBox(25);
    HBox hSliderBoxSlider = new HBox(25);
    HBox hFooterBox = new HBox(25);
    HBox hFooterBoxDiv = new HBox(25);


    @Override
    public void start(Stage stage) throws Exception {

        //Hier wird die Json-Datei geladen
        String jsonDateiPfad = ("/PWGenString.json");
        JsonNode jsonNode = loadJsonFromResources(jsonDateiPfad);

        if (jsonNode != null) {
            // Zugriff auf die einzelnen Strings in der JSON-Datei + Lable anpassen
            String StringHeader = jsonNode.get("StringHeader").asText();
            String StringText = jsonNode.get("StringText").asText();
            String StringImpressum = jsonNode.get("StringImpressum").asText();
            String StringSupport = jsonNode.get("StringSupport").asText();
            String StringlPwTrue = jsonNode.get("StringlPwTrue").asText();
            String StringlSliderField = jsonNode.get("StringlSliderField").asText();
            String StinglPWGespeichert = jsonNode.get("StringlPWGespeichert").asText();
            Label StringHeaderLabel = new Label(StringHeader);
            Label StringTextLabel = new Label(StringText);
            Label StringImpressumLabel = new Label(StringImpressum);
            Label StringSupportLabel = new Label(StringSupport);
            Label StringlPwTrueLabel = new Label(StringlPwTrue);
            Label StringlSliderFieldLabel = new Label(StringlSliderField);
            Label StinglPWGespeichertLabel = new Label(StinglPWGespeichert);
            TextField tf1 = new TextField();
            tf1.setEditable(true);
            tf1.setFocusTraversable(Boolean.FALSE);
            tf1.setPromptText("Bitte drücken Sie den Button...");
            tf1.setMaxWidth(425);
            Button b1 = new Button("Erstelle neues Passwort");
            CheckBox cBox1 = new CheckBox("Großbuchstaben");
            //Standardeinstellung
            cBox1.setSelected(true);
            CheckBox cBox2 = new CheckBox("Kleinbuchstaben");
            CheckBox cBox3 = new CheckBox("Zahlen");
            CheckBox cBox4 = new CheckBox("Sonderzeichen");
            CheckBox cBox5 = new CheckBox("Umlaute");
            CheckBox cBox6 = new CheckBox("Null");
            CheckBox cBox7 = new CheckBox("In Datei Speichern");

            //Konstruktor
            PWAlgo firstPWGen = new PWAlgo(cBox1.isSelected(),
                    cBox2.isSelected(), cBox3.isSelected(),
                    cBox4.isSelected(), cBox5.isSelected(),
                    cBox6.isSelected(),cBox7.isSelected(), 16);

            //Ausgabe des aktuellen True/False Wert der Checkbox
            cBox1.setOnAction(actionEvent -> {
                System.out.println(cBox1.isSelected());
                firstPWGen.setWithUppercase(cBox1.isSelected());
            });
            cBox2.setOnAction(actionEvent -> {
                firstPWGen.setWithLowercase(cBox2.isSelected());
                System.out.println(cBox2.isSelected());
            });
            cBox3.setOnAction(actionEvent -> {
                firstPWGen.setWithNumbers(cBox3.isSelected());
                System.out.println(cBox3.isSelected());
            });
            cBox4.setOnAction(actionEvent -> {
                firstPWGen.setWithSonderzeichen(cBox4.isSelected());
                System.out.println(cBox4.isSelected());
            });
            cBox5.setOnAction(actionEvent -> {
                firstPWGen.setWithUmlaute(cBox5.isSelected());
                System.out.println(cBox5.isSelected());
            });
            cBox6.setOnAction(actionEvent -> {
                firstPWGen.setWithNull(cBox6.isSelected());
                System.out.println(cBox6.isSelected());
            });
            cBox7.setOnAction(actionEvent -> {
                firstPWGen.setWithOutput(cBox7.isSelected());
                System.out.println(cBox7.isSelected());
            });

            Slider sl1 = new Slider(1, 64, 16);
            //Gib den aktuellen Wert in der Console aus
            sl1.valueProperty().addListener((v, oldValue, newValue) -> {
                sl1.setValue(newValue.intValue());
                int auswahl = newValue.intValue();
                //Gibt die Länge an den PWAlgo weiter
                firstPWGen.setLength(auswahl);
                StringlSliderFieldLabel.setText("Erstelle neues Passwort: " + auswahl);
                System.out.println(sl1.getValue());
            });

            //Anzeige der Tick, große Striche
            sl1.setShowTickLabels(true);
            //Kleine Strichen
            sl1.setShowTickMarks(true);
            sl1.setPrefWidth(850);
            sl1.setMajorTickUnit(5);
            sl1.setMinorTickCount(5);
            //Tick Marker: Beispiel: 1-6-11-16-21-26-31-36-41-46-51-56-61-64
            sl1.setLabelFormatter(new StringConverter<Double>() {
                @Override
                public String toString(Double value) {
                    int anzeigeZahl = (int) Math.round(value);
                    return anzeigeZahl + "";
                }
                @Override
                public Double fromString(String s) {
                    return null;
                }
            });
            //ID setzen für CSS:
            vBoxMain.setId("vBoxMain");
            hHeaderBox.setId("hHeaderBox");
            hTextBox.setId("hTextBox");
            hBodyBox.setId("hBodyBox");
            hBodyBoxLinks.setId("hBodyBoxLinks");
            hBodyBoxRechts.setId("hBodyBoxRechts");
            hSliderBox.setId("hSliderBox");
            hSliderBoxText.setId("hSliderBoxText");
            hSliderBoxSlider.setId("hSliderBoxSlider");
            hFooterBox.setId("hFooterBox");
            hFooterBoxDiv.setId("hFooterBoxDiv");
            StringHeaderLabel.setId("labelHeader");
            StringTextLabel.setId("lText");
            StinglPWGespeichertLabel.setId("lgespeichert");
            tf1.setId("tf1");
            b1.setId("b1");
            StringlPwTrueLabel.setId("lPwTrue");
            StringlSliderFieldLabel.setId("lSliderField");
            sl1.setId("sl1");
            StringImpressumLabel.setId("impressum");
            StringSupportLabel.setId("support");
            //Hinzufügen der einzelnen Objekte in die entsprechenden Boxen
            hHeaderBox.getChildren().add(StringHeaderLabel);
            hTextBox.getChildren().add(StringTextLabel);
            hBodyBoxLinks.getChildren().addAll(tf1, b1, StringlPwTrueLabel,StinglPWGespeichertLabel );
            hBodyBoxRechts.getChildren().addAll(cBox1, cBox2, cBox3, cBox4, cBox5, cBox6,cBox7);
            hBodyBox.getChildren().addAll(hBodyBoxLinks, hBodyBoxRechts);
            hSliderBox.getChildren().addAll(hSliderBoxText, hSliderBoxSlider);
            hSliderBoxText.getChildren().add(StringlSliderFieldLabel);
            hSliderBoxSlider.getChildren().add(sl1);
            hFooterBox.getChildren().add(hFooterBoxDiv);
            hFooterBoxDiv.getChildren().addAll(StringImpressumLabel, StringSupportLabel);
            vBoxMain.getChildren().addAll(hHeaderBox, hTextBox, hBodyBox, hSliderBox, hFooterBox);
            //Scene wird erstellt
            Scene mainScene = new Scene(vBoxMain, 1250, 950);
            //Datei musste in den 'resourcen' verschoben werden
            URL cssUrl = this.getClass().getResource("/PWGenCSS.css");
            if (cssUrl == null) {
                System.out.println("Die CSS-Datei wurde nicht gefunden!");
                System.exit(6);
            } else {
                System.out.println("Die CSS-Datei wurde gefunden!");
            }
            //Stylesheet wird angewendet
            mainScene.getStylesheets().add(cssUrl.toString());
            stage.setScene(mainScene);
            stage.setTitle("Self made PW Generator");
            stage.show();

            b1.setOnAction(actionEvent -> {
                String passwort = firstPWGen.publicGeneratePassword(
                        firstPWGen.getLength(), firstPWGen.getWithUppercase(),
                        firstPWGen.getWithNumbers(), firstPWGen.getWithSonderzeichen(),
                        firstPWGen.getWithLowercase(), firstPWGen.getWithUmlaute(),
                        firstPWGen.getWithNull(), firstPWGen.getWithOutput());

                tf1.setText(passwort);
                StringlPwTrueLabel.setText("\n\nPasswort wurde erfolgreich generiert!");
                if (firstPWGen.getWithOutput()) {
                    StinglPWGespeichertLabel.setText("Passwort wurde gespeichert!");
                } else {
                    StinglPWGespeichertLabel.setText("Passwort wurde nicht gespeichert!");
                }
            });
        }
    }
    public static void main(String[] args) {
        launch();
    }
    public static JsonNode loadJsonFromResources(String jsonFilePath) {
        try {
            // Lade die Datei als InputStream aus dem Ressourcenordner
            URL resource = PWGenerator.class.getResource(jsonFilePath);
            if (resource == null) {
                System.out.println("Die JSON-Datei wurde nicht gefunden!");
                return null;
            } else {
                System.out.println("Die JSON-Datei wurde gefunden!");
            }
            // Verwende Jackson's ObjectMapper, um die Datei zu laden
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}





