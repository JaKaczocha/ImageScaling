package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;


import javafx.scene.image.Image;

import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {






    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        scene = new Scene(loadFXML("layout2"));
        primaryStage.setScene(scene);
<<<<<<< HEAD
        primaryStage.setTitle("Interpolacja Obrazu");
=======

        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("ki.jpg")));

>>>>>>> feature-gui_update
        primaryStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();

        String fileName3 = "output.jpg"; // nazwa pliku
        File file3 = new File(fileName3); // tworzenie obiektu klasy File
        boolean deleted3 = file3.delete(); // usuwanie pliku i zapisanie wyniku w zmiennej deleted
        String fileName2 = "temporary.png";
        File file2 = new File(fileName2);
        boolean deleted2 = file2.delete();
        String fileName = "temporary.bnp"; // nazwa pliku
        File file = new File(fileName); // tworzenie obiektu klasy File
        boolean deleted = file.delete(); // usuwanie pliku i zapisanie wyniku w zmiennej deleted

        String fileName1 = "tmp.png"; // nazwa pliku
        File file1 = new File(fileName1); // tworzenie obiektu klasy File
        boolean deleted1 = file1.delete(); // usuwanie pliku i zapisanie wyniku w zmiennej deleted
<<<<<<< HEAD
=======
        String fileName4 = "tmpInputImage.png";
        File file4 = new File(fileName4);
        file4.delete();
>>>>>>> feature-gui_update
    }

}