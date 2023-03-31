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
    private Slider widthSlider;
    @FXML
    private Slider heightSlider;

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


        String fileName = "temporary.png"; // nazwa pliku
        File imageFile = new File(fileName); // tworzenie obiektu klasy File
        ImageIO.write(outputImage, "png", imageFile); // zapis obrazka do pliku

        if (imageFile.isFile()) {
            System.out.println("made image: " + imageFile.getAbsolutePath());
            ImageViewer imageViewer = new ImageViewer(imageFile);
        }
    }


    @FXML
    private void saveImage() throws IOException {
        // Get the path to the project directory
        String userDir = System.getProperty("user.dir");
        File projectDir = new File(userDir);

// Create a file selection dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz jako...");
        fileChooser.setInitialDirectory(projectDir);

// Add file filter for * format
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Plik PNG (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        extFilter = new FileChooser.ExtensionFilter("Plik JPG (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        extFilter = new FileChooser.ExtensionFilter("Plik JPEG (*.jpeg)", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        extFilter = new FileChooser.ExtensionFilter("Plik BMP (*.bmp)", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);
// Display a dialog and download the selected file
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                // Convert BufferedImage to Image and save to file
                ImageIO.write(outputImage, "png", file);

                // Display success notification
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Zapisano obrazek");
                alert.setHeaderText(null);
                alert.setContentText("Obrazek został zapisany w pliku: " + file.getAbsolutePath());
                alert.showAndWait();
            } catch (IOException ex) {
                // Display error notification
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

        // create a new image with the required dimensions
        outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());

        // scale original photo to new dimensions
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

    }



}
