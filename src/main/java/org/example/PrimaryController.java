package org.example;

import java.awt.*;
import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class PrimaryController {

    File selectedFile;
    ImageView imageV;
    BufferedImage outputImage;
    BufferedImage inputImage;

    @FXML
    private ImageView imageBefore;

    @FXML
    private ImageView imageAfter;

    @FXML
    private void selectImageButtonPressed() throws IOException { // wczytywanie obrazu - działa (!!!)
        // Utwórz okno wyboru pliku
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik obrazka");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Obrazki", "*.jpg", "*.jpeg", "*.png", "*.bmp"));

        // Wybierz plik obrazka
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Wczytaj plik obrazka i wyświetl go w ImageView
            Image image = new Image(selectedFile.toURI().toString(), 250, 250, true, false);
            imageBefore.setImage(image);
            inputImage = ImageIO.read(selectedFile);
        }

    }

    private int method;

    @FXML
    private Label myLabel;
    @FXML
    private RadioButton bilinear, bicubic, nearestNeighbor;

    int metoda = 1; //3 - dwuliniowa, 2 - dwukubiczna, 1 - najbliższego sąsiada
    public void getMethod(ActionEvent event) { // wybieranie metody (chyba nie działa, trzeba poprawic, ale nie sprawdzalem)

        if (bilinear.isSelected()){
            metoda = 3;
        }

        else if(bicubic.isSelected()){
            metoda = 2;
        }

        else if(nearestNeighbor.isSelected()){
            metoda = 1;
        }

    }


    @FXML
    private void applyChangesButtonPressed() throws IOException {
        int Width = 500;
        int Height = 500;

        if(nearestNeighbor.isSelected()) {
            method = 1;
        }
        else if(bilinear.isSelected()) {
            method = 2;
        }
        else if(bicubic.isSelected()) {
            method = 3;
        }



        if(method == 1) {
            nearestNeighbor(selectedFile,Width,Height);
        }
        else if( method == 2) {
            bilinear(selectedFile,Width,Height);
        }
        else if( method == 3) {
            bicubic(selectedFile, Width, Height);
        }

        else {
            return;
        }


        String fileName = "temporary.png"; // nazwa pliku
        File imageFile = new File(fileName); // tworzenie obiektu klasy File
        ImageIO.write(outputImage, "png", imageFile); // zapis obrazka do pliku

        if (imageFile.isFile()) {
            System.out.println("made image: " + imageFile.getAbsolutePath());


            Image image = new Image(imageFile.toURI().toString());
            imageAfter.setImage(image);
        }
    }

    public void nearestNeighbor(File inputFile, int newWidth, int newHeight) throws IOException {
        inputImage = ImageIO.read(inputFile);

        // create a new image with the required dimensions
        outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());

        // scale original photo to new dimensions using nearest neighbor interpolation
        Graphics2D g2d = outputImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        // save the new image to a file or use it as needed
        ImageIO.write(outputImage, "jpg", new File("output.jpg"));
    }
    public void bilinear(File inputFile, int newWidth, int newHeight) throws IOException {
        inputImage = ImageIO.read(inputFile);

        // create a new image with the required dimensions
        outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());

        // scale original photo to new dimensions using bilinear interpolation
        Graphics2D g2d = outputImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        // save the new image to a file or use it as needed
        ImageIO.write(outputImage, "jpg", new File("output.jpg"));
    }
    public void bicubic(File inputFile, int newWidth, int newHeight) throws IOException {
        inputImage = ImageIO.read(inputFile);

        // create a new image with the required dimensions
        outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());

        // scale original photo to new dimensions using bicubic interpolation
        Graphics2D g2d = outputImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        // save the new image to a file or use it as needed
        ImageIO.write(outputImage, "jpg", new File("output.jpg"));
    }

//to zostalo z poprzedniego kodu:
//    @FXML
//    private void saveImage() throws IOException {
//        // Get the path to the project directory
//        String userDir = System.getProperty("user.dir");
//        File projectDir = new File(userDir);
//
//// Create a file selection dialog
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Zapisz jako...");
//        fileChooser.setInitialDirectory(projectDir);
//
//// Add file filter for * format
//        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Plik PNG (*.png)", "*.png");
//        fileChooser.getExtensionFilters().add(extFilter);
//        extFilter = new FileChooser.ExtensionFilter("Plik JPG (*.jpg)", "*.jpg");
//        fileChooser.getExtensionFilters().add(extFilter);
//        extFilter = new FileChooser.ExtensionFilter("Plik JPEG (*.jpeg)", "*.jpeg");
//        fileChooser.getExtensionFilters().add(extFilter);
//        extFilter = new FileChooser.ExtensionFilter("Plik BMP (*.bmp)", "*.bmp");
//        fileChooser.getExtensionFilters().add(extFilter);
//// Display a dialog and download the selected file
//        File file = fileChooser.showSaveDialog(null);
//
//        if (file != null) {
//            try {
//                // Convert BufferedImage to Image and save to file
//                ImageIO.write(outputImage, "png", file);
//
//                // Display success notification
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Zapisano obrazek");
//                alert.setHeaderText(null);
//                alert.setContentText("Obrazek został zapisany w pliku: " + file.getAbsolutePath());
//                alert.showAndWait();
//            } catch (IOException ex) {
//                // Display error notification
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Błąd zapisu");
//                alert.setHeaderText(null);
//                alert.setContentText("Wystąpił błąd podczas zapisywania obrazka: " + ex.getMessage());
//                alert.showAndWait();
//            }
//        }
//    }





}
