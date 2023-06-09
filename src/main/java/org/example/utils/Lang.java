package org.example.utils;

import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Map;

public class Lang {

    private final static Map<LangChoice, Map<LangEnum, String>> langMap;
    private final static Map<LangEnum, String> engMap;
    private final static Map<LangEnum, String> plMap;

    static {
        engMap = new HashMap<>();
        engMap.put(LangEnum.APP_TITLE, "IMAGE SCALING");
        engMap.put(LangEnum.SETTINGS, "Settings");
        engMap.put(LangEnum.LANGUAGES, "Languages");
        engMap.put(LangEnum.LOAD, "Load");
        engMap.put(LangEnum.SAVE, "Save");
        engMap.put(LangEnum.CHOOSE_METHOD, "Choose interpolation method:");
        engMap.put(LangEnum.NEAREST_NEIGHBOUR, "Nearest neighbour");
        engMap.put(LangEnum.BICUBIC, "Bicubic");
        engMap.put(LangEnum.BILINEAR, "Bilinear");
        engMap.put(LangEnum.INTERPOLATION, "Scale:");
        engMap.put(LangEnum.INPUT_ZOOM, "Input zoom:");
        engMap.put(LangEnum.OUTPUT_ZOOM, "Output zoom:");

        plMap = new HashMap<>();
        plMap.put(LangEnum.APP_TITLE, "SKALOWANIE OBRAZÓW");
        plMap.put(LangEnum.SETTINGS, "Ustawienia");
        plMap.put(LangEnum.LANGUAGES, "Języki");
        plMap.put(LangEnum.LOAD, "Wczytaj");
        plMap.put(LangEnum.SAVE, "Zapisz");
        plMap.put(LangEnum.CHOOSE_METHOD, "Wybierz metodę interpolacji:");
        plMap.put(LangEnum.NEAREST_NEIGHBOUR, "Najbliższego sąsiada");
        plMap.put(LangEnum.BICUBIC, "Dwukubiczna");
        plMap.put(LangEnum.BILINEAR, "Dwuliniowa");
        plMap.put(LangEnum.INTERPOLATION, "Skala:");
        plMap.put(LangEnum.INPUT_ZOOM, "Przybliżenie wejścia:");
        plMap.put(LangEnum.OUTPUT_ZOOM, "Przybliżenie wyjścia:");

        langMap = new HashMap<>();
        langMap.put(LangChoice.ENG, engMap);
        langMap.put(LangChoice.PL, plMap);
    }


    // złap referencje do elementów z textem do zmiany (które będą przekazane przy inicializacji instancji)
    private Menu settingsMenu;
    private Menu languageMenu;
    private Label appTitle;
    private Button load;
    private Button save;
    private Label chooseMethod;
    private RadioButton nearestNeighbor;
    private RadioButton bicubic;
    private RadioButton bilinear;
    private Label interpolation;
    private Label inputZoom;
    private Label outputZoo;

    public Lang() {}
    public Lang(Menu settingsMenu,
                Menu languageMenu,
                Label appTitle,
                Button load,
                Button save,
                Label chooseMethod,
                RadioButton nearestNeighbor,
                RadioButton bicubic,
                RadioButton bilinear,
                Label interpolation,
                Label inputZoom,
                Label outputZoo) {

        this.settingsMenu = settingsMenu;
        this.languageMenu = languageMenu;
        this.appTitle = appTitle;
        this.load = load;
        this.save = save;
        this.chooseMethod = chooseMethod;
        this.nearestNeighbor = nearestNeighbor;
        this.bicubic = bicubic;
        this.bilinear = bilinear;
        this.interpolation = interpolation;
        this.inputZoom = inputZoom;
        this.outputZoo = outputZoo;
    }

    public void setLanguage(LangChoice language) {
        Map<LangEnum, String> selectedLangMap = langMap.get(language);

        settingsMenu.setText(selectedLangMap.get(LangEnum.SETTINGS));
        languageMenu.setText(selectedLangMap.get(LangEnum.LANGUAGES));
        appTitle.setText(selectedLangMap.get(LangEnum.APP_TITLE));
        load.setText(selectedLangMap.get(LangEnum.LOAD));
        save.setText(selectedLangMap.get(LangEnum.SAVE));
        chooseMethod.setText(selectedLangMap.get(LangEnum.CHOOSE_METHOD));
        nearestNeighbor.setText(selectedLangMap.get(LangEnum.NEAREST_NEIGHBOUR));
        bicubic.setText(selectedLangMap.get(LangEnum.BICUBIC));
        bilinear.setText(selectedLangMap.get(LangEnum.BILINEAR));
        interpolation.setText(selectedLangMap.get(LangEnum.INTERPOLATION));
        inputZoom.setText(selectedLangMap.get(LangEnum.INPUT_ZOOM));
        outputZoo.setText(selectedLangMap.get(LangEnum.OUTPUT_ZOOM));
    }
}
