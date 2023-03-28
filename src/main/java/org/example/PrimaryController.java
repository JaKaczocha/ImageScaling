package org.example;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrimaryController {

    File selectedFile;
    @FXML
    private void switchToSecondary() throws IOException {

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
}
