package com.egotec.pwgengui;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class PWAlgo {

    private boolean withUppercase = true;
    private boolean withLowercase = false;
    private boolean withNumbers = true;
    private boolean withSonderzeichen = false;
    private boolean withUmlaute = false;
    private boolean withNull = false;
    private int length = 16;
    //Speichern des PW
    private boolean withOutput = false;
    //Speichern der Voreinstellungen
    private boolean withSettings = false;
    // nicht relevant
    private String leereZeile = " ";
    private boolean auswahl = false;
    private boolean debug = false;

    public boolean getWithUppercase() {
        return withUppercase;
    }
    public void setWithUppercase(boolean withUppercase) {
        this.withUppercase = withUppercase;
    }
    public boolean getWithLowercase() {
        return withLowercase;
    }
    public void setWithLowercase(boolean withLowercase) {
        this.withLowercase = withLowercase;
    }
    public boolean getWithNumbers() {
        return withNumbers;
    }
    public void setWithNumbers(boolean withNumbers) {
        this.withNumbers = withNumbers;
    }
    public boolean getWithSonderzeichen() {
        return withSonderzeichen;
    }
    public void setWithSonderzeichen(boolean withSonderzeichen) {
        this.withSonderzeichen = withSonderzeichen;
    }
    public boolean getWithUmlaute() {
        return withUmlaute;
    }
    public void setWithUmlaute(boolean withUmlaute) {
        this.withUmlaute = withUmlaute;
    }
    public boolean getWithNull() {return withNull;}
    public void setWithNull(boolean withNull) {
        this.withNull = withNull;
    }
    public boolean getWithOutput() {
        return withOutput;
    }
    public void setWithOutput(boolean withOutput) {
        this.withOutput = withOutput;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public boolean getWithSettings() {return withSettings;}
    public void setWithSettings(boolean withSettings) {this.withSettings = withSettings;}

    public PWAlgo(boolean withUppercase, boolean withLowercase,
                  boolean withNumbers, boolean withSonderzeichen,
                  boolean withUmlaute, boolean withNull, boolean withOutput, int length, boolean withSettings) {

        this.withUppercase = withUppercase;
        this.withLowercase = withLowercase;
        this.withNumbers = withNumbers;
        this.withSonderzeichen = withSonderzeichen;
        this.withUmlaute = withUmlaute;
        this.withNull = withNull;
        this.withOutput = withOutput;
        this.withSettings = withSettings;
        this.length = length;

    }

    private String generatePassword(int length, boolean uppercase,
                                    boolean numbers, boolean special,
                                    boolean lowercase, boolean umlaute,
                                    boolean zeros, boolean output, boolean settings) {

        Random random = new Random();
        String password = "";
        int count = 0;

        if (!uppercase && !numbers && !special && !lowercase && !umlaute) {
            password = "Sie haben keine Auswahl getroffen!";
        } else {

            while (count < length) {
                int zufallsAuswahl = random.nextInt(6);

                if (zufallsAuswahl == 0 && uppercase) {
                    //Großbuchstaben
                    int testGrBuch = random.nextInt(26) + 65;
                    password += (char) testGrBuch;
                    count++;
                } else if (zufallsAuswahl == 1 && lowercase) {
                    //Kleinbuchstaben
                    int testKlBuch = random.nextInt(26) + 97;
                    password += (char) testKlBuch;
                    count++;
                } else if (zufallsAuswahl == 2 && numbers) {
                    //Zahl
                    int zahl = random.nextInt(8) + 49;
                    password += (char) zahl;
                    count++;
                } else if (zufallsAuswahl == 3 && special) {
                    // Sonderzeichen als Arry
                    char[] sonderzeichenCharArray =
                            {'!', '"', '§', '$', '%', '&', '/',
                                    '{', '(', '[', ')', ']', '=', '}',
                                    '?', '+', '*', '~', '#', '@', '€'};
                    char zufallsSonderzeichen = sonderzeichenCharArray[random.nextInt(sonderzeichenCharArray.length)];
                    password += (char) zufallsSonderzeichen;
                    count++;
                } else if (zufallsAuswahl == 4 && zeros) {
                    //Zahl null
                    char zahlNull = 48;
                    password += (char) zahlNull;
                    count++;
                } else if (zufallsAuswahl == 5 && umlaute) {
                    // Umlaute einschließen
                    char[] umlautCharArray = {'Ä', 'ä', 'Ü', 'ü', 'Ö', 'ö'};
                    char zufallsUmlaut = umlautCharArray[random.nextInt(umlautCharArray.length)];
                    password += (char) zufallsUmlaut;
                    count++;
                }
            }

            if (output) {
                JSONObject jOb = new JSONObject();
                jOb.put("password:", password);
                File passwortSpeichern = new File("src/main/java/com/egotec/pwgengui/TestPWJSON.json");

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(passwortSpeichern));
                    writer.write(jOb.toString(4));
                    writer.close();
                    System.out.println("Passwort " + password + " wurde in der Datei " + passwortSpeichern.getAbsolutePath() + " gespeichert");
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
            if (settings) {
                JSONObject letzteEinstellung = new JSONObject();
                letzteEinstellung.put("Länge", length);
                letzteEinstellung.put("Großbuchstaben", uppercase);
                letzteEinstellung.put("Kleinbuchstaben", lowercase);
                letzteEinstellung.put("Nummern", numbers);
                letzteEinstellung.put("Sonderzeichen", special);
                letzteEinstellung.put("Umlaute", umlaute);
                letzteEinstellung.put("Nullen", zeros);
                letzteEinstellung.put("PW Speicher", output);
                letzteEinstellung.put("Settings Speicher", true);
                File vorEinstellungen = new File("src/main/resources/PWGenSettings.json");

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(vorEinstellungen));
                    writer.write(letzteEinstellung.toString(4));
                    writer.close();
                    System.out.println("Einstellungen wurden in der Datei " + vorEinstellungen.getAbsolutePath() + " gespeichert");
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }
        return password;
    }

    //Dadruch kann ich in einerander Klasse die privte
    public String publicGeneratePassword(int length, boolean uppercase,
                                         boolean numbers, boolean special,
                                         boolean lowercase, boolean umlaute,
                                         boolean zeros, boolean output, boolean settings) {
        return generatePassword(length, uppercase, numbers, special, lowercase, umlaute, zeros, output, settings);
    }
}
