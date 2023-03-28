package org.example;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class ImageChooserButton extends Button {

    public File selectedFile;

    public ImageChooserButton(String text, Stage primaryStage) {
        super(text);

        this.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select image:");

            // set the start directory to the project directory
            String currentDir = System.getProperty("user.dir");
            fileChooser.setInitialDirectory(new File(currentDir));

            // add a filter for image files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("\n" +
                    "Graphic files", "*.jpg", "*.jpeg", "*.png", "*.bmp");
            fileChooser.getExtensionFilters().add(extFilter);

            selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                System.out.println("Selected image: " + selectedFile.getAbsolutePath());
            }
        });
    }
}