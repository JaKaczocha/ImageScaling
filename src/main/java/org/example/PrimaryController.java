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

    @FXML
    private ImageView imageBefore;

    @FXML
    private ImageView imageAfter;

    @FXML
    private void selectImageButtonPressed() { // wczytywanie obrazu - działa (!!!)
        // Utwórz okno wyboru pliku
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik obrazka");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Obrazki", "*.jpg", "*.jpeg", "*.png", "*.bmp"));

        // Wybierz plik obrazka
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Wczytaj plik obrazka i wyświetl go w ImageView
            Image image = new Image(selectedFile.toURI().toString(), 250, 250, true, false);
            imageBefore.setImage(image);
        }
    }



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

    public Image nearestScale(ImageView imageV, int scaleBig) { //chat powiedzial ze to metoda najblizszego sasiada
        int scale = scaleBig / 100;
        Image imageOrg = imageV.getImage();
        int newWidth = (int) (imageOrg.getWidth() * scale);
        int newHeight = (int) (imageOrg.getHeight() * scale);

        WritableImage imageScaled = new WritableImage(newWidth, newHeight);
        PixelReader pixelReader = imageOrg.getPixelReader();
        PixelWriter pixelWriter = imageScaled.getPixelWriter();

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int oldX = (int) (x / scale);
                int oldY = (int) (y / scale);
                pixelWriter.setArgb(x, y, pixelReader.getArgb(oldX, oldY));
            }
        }
        return imageScaled;
    }



    @FXML
    private void applyChangesButtonPressed(){
        int method = 1; //tymczasowe bo nie wiem jak tu przekazac wybór metody (nie wiem tez jak zrobic zeby w ogole dzialal)
        int skala = 210; //to samo co wyzej
        if (method == 1) {
            Image scaled = nearestScale(imageBefore, skala); // nie wiem czy się skaluje, bo nie zrobiłem zapisu i nie moglem sprawdzic
            imageAfter.setImage(scaled);
        }
        else if (method == 2){
            //bicubicScale();
        }
        else if (method == 3){
            //bilinearScale();
        }
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
