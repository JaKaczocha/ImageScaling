package org.example;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageViewer extends Stage {

    public ImageViewer(File file) throws FileNotFoundException {
        // Tworzenie obiektu Image na podstawie pliku
        Image image = new Image(new FileInputStream(file));

        // Tworzenie obiektu ImageView na podstawie obiektu Image
        ImageView imageView = new ImageView(image);

        // Tworzenie obiektu Pane jako kontenera dla ImageView
        Pane root = new Pane(imageView);

        // Tworzenie sceny na podstawie Pane i ustawienie rozmiaru na rozmiar obrazu
        Scene scene = new Scene(root, image.getWidth(), image.getHeight());

        // Tworzenie nowego okna i ustawienie sceny
        Stage stage = new Stage();
        stage.setScene(scene);

        // blokowanie rozciagania
        stage.setResizable(false);
        // Wyświetlenie okna
        stage.show();
    }
}