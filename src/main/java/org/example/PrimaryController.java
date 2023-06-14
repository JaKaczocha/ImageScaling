package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import org.example.utils.Lang;
import org.example.utils.LangChoice;

import javax.imageio.ImageIO;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PrimaryController {



    @FXML
    private Button selectImageButton;
    @FXML
    private Button saveImageButton;
    @FXML
    private javafx.scene.control.Label selectMethod;
    @FXML
    private javafx.scene.control.Label interpolation;
    @FXML
    private javafx.scene.control.Label zoomInputLabel;
    @FXML
    private javafx.scene.control.Label zoomOutputLabel;
    @FXML
    private Slider zoomInputSlider;
    @FXML
    private Slider mySlider;
    @FXML
    Slider zoomSlider;

    private Menu settingsMenu;
    private Menu languageMenu;

    public static final String PL = "Polski";
    public static final String EN = "English";
    File selectedFile;
    ImageView imageV;
    BufferedImage outputImage;

    BufferedImage inputImage;
    int widthBefore;
    int heightBefore;
    @FXML
    private ImageView imageBefore;
    @FXML
    private ImageView imageAfter;
    @FXML
    private VBox root;

    boolean selected = false;

    @FXML
    private TextArea inputSize;
    @FXML
    private TextArea outputSize;
    private Lang langUtility;

    @FXML
    private void selectImageButtonPressed() throws IOException {
        // Utwórz okno wyboru pliku
        FileChooser fileChooser = new FileChooser();

        // Sprawdź istnienie folderu
        String imageFolderPath = System.getProperty("user.dir") + "/image";
        File imageFolder = new File(imageFolderPath);
        if (!imageFolder.exists() || !imageFolder.isDirectory()) {
            // Folder nie istnieje lub nie jest katalogiem, nie ustawiaj domyślnego folderu
            imageFolder = null;
        }

        fileChooser.setInitialDirectory(imageFolder);
        fileChooser.setTitle("Wybierz plik obrazka");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Obrazki", "*.jpg", "*.jpeg", "*.png", "*.bmp"));

        // Wybierz plik obrazka
        File selectedFileTmp = fileChooser.showOpenDialog(null);
        if (selectedFileTmp != null) {

            imageAfter.setFitHeight(0);
            imageAfter.setFitWidth(0);

            selectedFile = selectedFileTmp;
            // Wczytaj plik obrazka i wyświetl go w ImageView
            Image image = new Image(selectedFile.toURI().toString());
            heightBefore = (int) image.getHeight();
            widthBefore = (int) image.getWidth();

            imageBefore.setFitHeight(image.getHeight());
            imageBefore.setFitWidth(image.getWidth());
            imageBefore.setPreserveRatio(true);
            imageBefore.setImage(image);
            inputImage = ImageIO.read(selectedFile);
            outputImage = null;
            imageAfter.setImage(null);
            selected = true;

            mySlider.setValue(100.0);
            zoomSlider.setValue(0.0);
            zoomInputSlider.setValue(0.0);
        }
    }


    private int method;

    @FXML
    private Label myLabel;
    @FXML
    private RadioButton bilinear, bicubic, nearestNeighbor;

    int metoda = 1; //3 - dwuliniowa, 2 - dwukubiczna, 1 - najbliższego sąsiada

    public void getMethod(ActionEvent event) { // wybieranie metody (chyba nie działa, trzeba poprawic, ale nie sprawdzalem)

        if (bilinear.isSelected()) {
            metoda = 3;
        } else if (bicubic.isSelected()) {
            metoda = 2;
        } else if (nearestNeighbor.isSelected()) {
            metoda = 1;
        }

    }


    @FXML
    private void applyChangesButtonPressed() throws IOException {


        int Width = (int) ((mySlider.getValue() / 100) * widthBefore);
        int Height = (int) ((mySlider.getValue() / 100) * heightBefore);
        System.out.println(Width + " " + Height);
        if (nearestNeighbor.isSelected()) {
            method = 1;
        } else if (bilinear.isSelected()) {
            method = 2;
        } else if (bicubic.isSelected()) {
            method = 3;
        }


        if (method == 1) {
            nearestNeighbor(selectedFile, Width, Height);
        } else if (method == 2) {
            bilinear(selectedFile, Width, Height);
        } else if (method == 3) {
            bicubic(selectedFile, Width, Height);
        } else {
            return;
        }


        String fileName = "temporary.png"; // nazwa pliku
        File imageFile = new File(fileName); // tworzenie obiektu klasy File
        ImageIO.write(outputImage, "png", imageFile); // zapis obrazka do pliku

        if (imageFile.isFile()) {
            System.out.println("made image: " + imageFile.getAbsolutePath());


            Image image = new Image(imageFile.toURI().toString());
            imageAfter.setImage(image);
            imageAfter.setFitHeight(image.getHeight());
            imageAfter.setFitWidth(image.getWidth());
            imageAfter.setPreserveRatio(true);
        }


        int value = (int) zoomSlider.getValue();
        zoom("temporary.png", value);

        String fileName2 = "tmp.png"; // nazwa pliku
        File imageFile2 = new File(fileName2); // tworzenie obiektu klasy File


        if (imageFile2.isFile()) {
            System.out.println("made image: " + imageFile2.getAbsolutePath());


            Image image = new Image(imageFile2.toURI().toString());
            imageAfter.setImage(image);
            imageAfter.setFitHeight(image.getHeight());
            imageAfter.setFitWidth(image.getWidth());
            imageAfter.setPreserveRatio(true);
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

    public double lastValue = 0.0;
    double lastValue2 = 0.0;
    double lastValue3 = 0.0;

    public void initialize() {
        // stwórz menu
        initMenu();
        // przekaż potrzebne parametry do Lang Utility
        langUtility = new Lang(settingsMenu,
                languageMenu,
                selectImageButton,
                saveImageButton,
                selectMethod,
                nearestNeighbor,
                bicubic,
                bilinear,
                interpolation,
                zoomInputLabel,
                zoomOutputLabel);


        mySlider.setValue(100.0);
        mySlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                return String.format("%.0f%%", n);
            }

            @Override
            public Double fromString(String s) {
                return null;
            }
        });

        zoomSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                return String.format("%.0fx", n);
            }

            @Override
            public Double fromString(String s) {
                return null;
            }
        });
        zoomInputSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                return String.format("%.0fx", n);
            }

            @Override
            public Double fromString(String s) {
                return null;
            }
        });


        zoomInputSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Kod do wykonania po zmianie wartości suwaka
            double value = newValue.doubleValue();
            double roundedValue = Math.floor(value);

            //System.out.println("Nowa wartość suwaka zoomInputSlider: " + value +" " +  roundedValue);

            //System.out.println(lastValue + " - " + roundedValue + " = " + (lastValue - roundedValue));
            if (Math.abs(Math.round(lastValue - roundedValue)) > 0.5) {
                if (selected) {


                    //System.out.println("!!!!!!!!!!" + roundedValue);

                    lastValue = roundedValue;
                    // Tworzenie BufferedImage
                    BufferedImage tmpInputImage = resizeImage(inputImage, (int) roundedValue);

                    try {
                        ImageIO.write(tmpInputImage, "png", new File("tmpInputImage.png"));
                        Image writableImage = new Image("file:tmpInputImage.png");
                        imageBefore.setImage(writableImage);
                        imageBefore.setFitWidth(writableImage.getWidth());
                        imageBefore.setFitHeight(writableImage.getHeight());
                        imageBefore.setPreserveRatio(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        zoomInputSlider.setOnMouseReleased(event -> {
            double snappedValue = Math.floor(zoomInputSlider.getValue());

            zoomInputSlider.setValue(snappedValue);

            // Wykonaj odpowiednie czynności po upuszczeniu suwaka na najbliższą mniejszą wartość
            //System.out.println("Zaokrąglona wartość suwaka zoomInputSlider: " + snappedValue);
        });


        //000000000000

        zoomSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Kod do wykonania po zmianie wartości suwaka
            double value = newValue.doubleValue();
            double roundedValue = Math.floor(value);

            //System.out.println("Nowa wartość suwaka zoomInputSlider: " + value +" " +  roundedValue);

            //System.out.println(lastValue2 + " - " + roundedValue + " = " + (lastValue2 - roundedValue));
            if (Math.abs(Math.round(lastValue2 - roundedValue)) > 0.5) {
                if (selected) {
                    try {
                        applyChangesButtonPressed();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        zoomSlider.setOnMouseReleased(event -> {
            double snappedValue = Math.floor(zoomSlider.getValue());

            zoomSlider.setValue(snappedValue);

            // Wykonaj odpowiednie czynności po upuszczeniu suwaka na najbliższą mniejszą wartość
            //System.out.println("Zaokrąglona wartość suwaka zoomSlider: " + snappedValue);
        });


        //11111111111111
        mySlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Kod do wykonania po zmianie wartości suwaka
            double value = newValue.doubleValue();
            double roundedValue = Math.floor(value);

            //System.out.println("Nowa wartość suwaka zoomInputSlider: " + value +" " +  roundedValue);

            System.out.println(lastValue3 + " - " + roundedValue + " = " + (lastValue3 - roundedValue));
            if (Math.abs(Math.round(lastValue3 - roundedValue)) > 0.5) {
                if (selected) {
                    try {
                        applyChangesButtonPressed();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        mySlider.setOnMouseReleased(event -> {
            double snappedValue = Math.floor(mySlider.getValue());

            mySlider.setValue(snappedValue);

            // Wykonaj odpowiednie czynności po upuszczeniu suwaka na najbliższą mniejszą wartość
            System.out.println("Zaokrąglona wartość suwaka zoomSlider: " + snappedValue);
        });


        //222222
        ToggleGroup metodyToggleGroup = new ToggleGroup();

        nearestNeighbor.setToggleGroup(metodyToggleGroup);
        bicubic.setToggleGroup(metodyToggleGroup);
        bilinear.setToggleGroup(metodyToggleGroup);

        metodyToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            if (selected) {
                try {
                    applyChangesButtonPressed();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        });
    }


    public void buttonSave() {
        if (outputImage == null) {
            return;
        }
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Obrazy", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(filter);

        // Sprawdź istnienie folderu
        String imageFolderPath = System.getProperty("user.dir") + "/image";
        File imageFolder = new File(imageFolderPath);
        if (!imageFolder.exists() || !imageFolder.isDirectory()) {
            // Folder nie istnieje lub nie jest katalogiem, nie ustawiaj domyślnego folderu
            imageFolder = null;
        }
        fileChooser.setInitialDirectory(imageFolder);

        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            try {
                String extension = getFileExtension(selectedFile);
                ImageIO.write(outputImage, extension, selectedFile);
            } catch (Exception e) {
                System.out.println("Błąd podczas zapisywania pliku: " + e.getMessage());
            }
        }
    }

    private String getFileExtension(File file) {
        String extension = "";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1);
        }
        return extension;
    }

    //-----------------------------------------------------------------------ffffffffffffffff
    public void zoom(String filePath, int multiplier) {
        try {
            // Wczytaj obraz z pliku
            BufferedImage image = ImageIO.read(new File(filePath));


            //BufferedImage imageinput = ImageIO.read(selectedFile);
            //inputImage = resizeImage(imageinput,multiplier);
            // Przeskaluj obraz za pomocą funkcji resizeArray
            outputImage = resizeImage(image, multiplier);

            // Zapisz przekształcony obraz do tego samego pliku
            ImageIO.write(outputImage, "png", new File("tmp.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static BufferedImage resizeImage(BufferedImage image, int multiplier) {
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = width * multiplier;
        int newHeight = height * multiplier;

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);

                // Skopiuj piksel do powiększonego obrazu wielokrotnie
                for (int i = 0; i < multiplier; i++) {
                    for (int j = 0; j < multiplier; j++) {
                        resizedImage.setRGB(x * multiplier + i, y * multiplier + j, pixel);
                    }
                }
            }
        }

        return resizedImage;
    }

    public static void saveImage(int[][] imageArray, String filePath) {
        try {
            int height = imageArray.length;
            int width = imageArray[0].length;

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixelValue = imageArray[y][x];
                    int rgb = (pixelValue << 16) | (pixelValue << 8) | pixelValue;
                    image.setRGB(x, y, rgb);
                }
            }

            File outputFile = new File("tmp.png");
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[][] convertTo2DArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] imageArray = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;
                int gray = (red + green + blue) / 3;
                imageArray[y][x] = gray;
            }
        }

        return imageArray;
    }
    //---------------------------------------------------

    private String getFileExtension(String filename) {
        String extension = "";
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            extension = filename.substring(dotIndex + 1);
        }
        return extension;
    }

    private void debug(String info) {
        root.getChildren().add(new javafx.scene.control.Label(info));
    }

    private void initMenu() {
        MenuBar menuBar = (MenuBar) root.getChildren().get(0);
        Menu settingsMenu = menuBar.getMenus().get(0);
        Menu languageMenu = (Menu) settingsMenu.getItems().get(0);
        // potrzebne do konstruktora LangUtils który będzie modyfikował text
        this.languageMenu = languageMenu;
        this.settingsMenu = settingsMenu;

        for (javafx.scene.control.MenuItem languageItem : languageMenu.getItems()) {
            languageItem.setOnAction(this::handleLanguageSelection);
        }
    }

    private void handleLanguageSelection(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String selectedLang = menuItem.getText();
        switch (selectedLang) {
            case PL:
                System.out.println("Wybrano język polski");
                langUtility.setLanguage(LangChoice.PL);
                break;
            case EN:
                System.out.println("Wybrano język angielski");
                langUtility.setLanguage(LangChoice.ENG);
                break;
        }
    }


}