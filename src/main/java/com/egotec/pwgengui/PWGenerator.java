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

import javax.swing.*;
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
    HBox hBodyBoxRechts = new HBox(25);
    VBox vBodyBoxRechtsLinks = new VBox(25);
    VBox vBodyBoxRechtsRechts = new VBox(25);
    VBox hSliderBox = new VBox(25);
    HBox hSliderBoxText = new HBox(25);
    HBox hSliderBoxSlider = new HBox(25);
    HBox hFooterBox = new HBox(25);
    HBox hFooterBoxDiv = new HBox(25);
    int lenght = 16;


    @Override
    public void start(Stage stage) throws Exception {

        //Hier wird die Json-Datei voller Strings geladen
        String jsonDateiPfadStrings = ("/PWGenString.json");
        JsonNode jsonNode1 = loadJsonFromResources(jsonDateiPfadStrings);

        //Hier wird die Json-Datei mit Settings geladen
        String jsonDateiPfadSettings = ("/PWGenSettings.json");
        JsonNode jsonNode2 = loadJsonFromResources(jsonDateiPfadSettings);

        if (jsonNode1 != null) {
            //Zugriff auf die einzelnen Strings in der JSON-Datei + Lable anpassen
            String StringHeader = jsonNode1.get("StringHeader").asText();
            String StringText = jsonNode1.get("StringText").asText();
            String StringImpressum = jsonNode1.get("StringImpressum").asText();
            String StringSupport = jsonNode1.get("StringSupport").asText();
            String StringlPwTrue = jsonNode1.get("StringlPwTrue").asText();
            String StringlSliderField = jsonNode1.get("StringlSliderField").asText();
            String StinglPWGespeichert = jsonNode1.get("StringlPWGespeichert").asText();
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
            CheckBox cBox7 = new CheckBox("In Datei speichern");
            CheckBox cBox8 = new CheckBox("Save Settings");
            //Checkboxen nehmen den Wert aus den Json (Safe-Settings) an
            if (jsonNode2 != null) {
                cBox1.setSelected(jsonNode2.get("Großbuchstaben").asBoolean());
                cBox2.setSelected(jsonNode2.get("Kleinbuchstaben").asBoolean());
                cBox3.setSelected(jsonNode2.get("Nummern").asBoolean());
                cBox4.setSelected(jsonNode2.get("Sonderzeichen").asBoolean());
                cBox5.setSelected(jsonNode2.get("Umlaute").asBoolean());
                cBox6.setSelected(jsonNode2.get("Nullen").asBoolean());
                cBox7.setSelected(jsonNode2.get("PW Speicher").asBoolean());
                cBox8.setSelected(jsonNode2.get("Settings Speicher").asBoolean());
                lenght = jsonNode2.get("Länge").asInt();
            }
            //Konstruktor
            PWAlgo firstPWGen = new PWAlgo(cBox1.isSelected(),
                    cBox2.isSelected(), cBox3.isSelected(),
                    cBox4.isSelected(), cBox5.isSelected(),
                    cBox6.isSelected(), cBox7.isSelected(), lenght, cBox8.isSelected());

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
            cBox8.setOnAction(actionEvent -> {
                firstPWGen.setWithSettings(cBox8.isSelected());
                System.out.println(cBox8.isSelected());
            });
            Slider sl1 = new Slider(1, 64, lenght);
            //Gib den aktuellen Wert in der Console aus: Min, Max, Standard
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
            hBodyBoxLinks.getChildren().addAll(tf1, b1, StringlPwTrueLabel, StinglPWGespeichertLabel);
            hBodyBoxRechts.getChildren().addAll(vBodyBoxRechtsLinks, vBodyBoxRechtsRechts);
            vBodyBoxRechtsLinks.getChildren().addAll(cBox1, cBox2, cBox3, cBox4);
            vBodyBoxRechtsRechts.getChildren().addAll(cBox5, cBox6, cBox7, cBox8);
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
                System.out.println("Die CSS-Datei " + cssUrl + " wurde gefunden!");
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
                        firstPWGen.getWithNull(), firstPWGen.getWithOutput(), firstPWGen.getWithSettings());

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
                System.out.println("Die JSON-Datei " + jsonFilePath + " wurde nicht gefunden!");
                return null;
            } else {
                System.out.println("Die JSON-Datei " + jsonFilePath + " wurde gefunden!");
            }
            // Verwende Jackson's ObjectMapper, um die Datei zu laden
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(resource);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e);
            return null;
        }
    }
}
