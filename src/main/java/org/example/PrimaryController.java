package org.example;

import java.awt.*;
import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class PrimaryController {

    File selectedFile;
    BufferedImage outputImage;
    BufferedImage inputImage;


    @FXML
    private void selectImage() throws IOException {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select image:");

                // set the start directory to the project directory
                String currentDir = System.getProperty("user.dir");
                fileChooser.setInitialDirectory(new File(currentDir));

                // add a filter for image files
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("\n" +
                        "Graphic files", "*.jpg", "*.jpeg", "*.png", "*.bmp");
                fileChooser.getExtensionFilters().add(extFilter);

                selectedFile = fileChooser.showOpenDialog(new Stage());
                if (selectedFile != null) {
                    System.out.println("Selected image: " + selectedFile.getAbsolutePath());
                    ImageViewer imageViewer = new ImageViewer(selectedFile);
                }
    }

    @FXML
    private Slider widthSlider;
    @FXML
    private Slider heightSlider;

    @FXML
    private void applyChanges() throws IOException {
        int Width = (int) widthSlider.getValue();
        int Height = (int) heightSlider.getValue();
        if(Width <= 0) {
            Width++;
        }
        if(Height <= 0) {
            Height++;
        }
        resizeImage(selectedFile, Width, Height);


        String fileName = "temporary.jpg"; // nazwa pliku
        File imageFile = new File(fileName); // tworzenie obiektu klasy File
        ImageIO.write(outputImage, "jpg", imageFile); // zapis obrazka do pliku

        if (imageFile != null) {
            System.out.println("made image: " + imageFile.getAbsolutePath());
            ImageViewer imageViewer = new ImageViewer(imageFile);
        }
    }


    @FXML
    private void saveImage() throws IOException {
        // Uzyskaj ścieżkę do katalogu projektu
        String userDir = System.getProperty("user.dir");
        File projectDir = new File(userDir);

// Utwórz okno dialogowe wyboru pliku
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz jako...");
        fileChooser.setInitialDirectory(projectDir);

// Dodaj filtr plików dla formatu PNG
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Plik PNG (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

// Wyświetl okno dialogowe i pobierz wybrany plik
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                // Konwertuj BufferedImage na Image i zapisz do pliku
                ImageIO.write(outputImage, "png", file);

                // Wyświetl powiadomienie o sukcesie
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Zapisano obrazek");
                alert.setHeaderText(null);
                alert.setContentText("Obrazek został zapisany w pliku: " + file.getAbsolutePath());
                alert.showAndWait();
            } catch (IOException ex) {
                // Wyświetl powiadomienie o błędzie
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd zapisu");
                alert.setHeaderText(null);
                alert.setContentText("Wystąpił błąd podczas zapisywania obrazka: " + ex.getMessage());
                alert.showAndWait();
            }
        }
    }





    public void resizeImage(File inputFile, int newWidth, int newHeight) throws IOException {
        inputImage = ImageIO.read(inputFile);

        // utwórz nowy obraz o wymaganych wymiarach
        outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());

        // skaluj oryginalne zdjęcie do nowych wymiarów
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

    }



}
