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
        // Create an Image from a file
        Image image = new Image(new FileInputStream(file));

        // Create an ImageView object from the Image object
        ImageView imageView = new ImageView(image);

        // Create a Pane object as a container for the ImageView
        Pane root = new Pane(imageView);

        // Create a scene from Pane and set the size to the size of the image
        Scene scene = new Scene(root, image.getWidth(), image.getHeight());

        // Create a new window and set the scene
        Stage stage = new Stage();
        stage.setScene(scene);

        // block stretching
        stage.setResizable(false);
        // Display the window
        stage.show();
    }
}