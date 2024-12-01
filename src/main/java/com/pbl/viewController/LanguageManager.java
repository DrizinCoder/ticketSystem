package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {
    private static Locale currentLocale = Locale.getDefault();
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("fxml.properties.language", currentLocale);
    private static final List<LanguageChange> listeners = new ArrayList<>();
    public static int languageController = 0;
    public static boolean FontSizeController = true;

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        resourceBundle = ResourceBundle.getBundle("fxml.properties.language", currentLocale);
    }

    public static String getString(String key) {
        return resourceBundle.getString(key);
    }

    public static void registerListener(LanguageChange listener) {
        listeners.add(listener);
    }

    public static void notifyListeners() {
        for (LanguageChange listener : listeners) {
            listener.onLocaleChange(currentLocale);
            listener.onLocalToggleFont();
        }
    }
}
