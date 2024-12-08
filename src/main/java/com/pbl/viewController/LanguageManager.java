package com.pbl.viewController;

import com.pbl.Interfaces.LanguageChange;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A classe `LanguageManager` gerencia a configuração de idioma e o carregamento de recursos de linguagem
 * na aplicação. Ela permite que o idioma da interface do usuário seja alterado dinamicamente e notifica
 * os ouvintes registrados sempre que o idioma ou o tamanho da fonte muda.
 */
public class LanguageManager {
    private static Locale currentLocale = Locale.getDefault();
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("fxml.properties.language", currentLocale);
    private static final List<LanguageChange> listeners = new ArrayList<>();
    public static int languageController = 0;
    public static boolean FontSizeController = true;

    /**
     * Define o idioma atual da aplicação.
     *
     * @param locale O novo idioma a ser configurado.
     */
    public static void setLocale(Locale locale) {
        currentLocale = locale;
        resourceBundle = ResourceBundle.getBundle("fxml.properties.language", currentLocale);
    }

    /**
     * Retorna a string associada à chave fornecida, de acordo com o idioma atual.
     *
     * @param key A chave do recurso de linguagem que se deseja obter.
     * @return O valor da string associada à chave fornecida no idioma atual.
     */
    public static String getString(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Registra um ouvinte que será notificado sempre que o idioma ou o tamanho da fonte mudar.
     *
     * @param listener O ouvinte a ser registrado.
     */
    public static void registerListener(LanguageChange listener) {
        listeners.add(listener);
    }

    /**
     * Notifica todos os ouvintes registrados sobre a mudança de idioma e/ou o tamanho da fonte.
     */
    public static void notifyListeners() {
        for (LanguageChange listener : listeners) {
            listener.onLocaleChange(currentLocale);
            listener.onLocalToggleFont();
        }
    }
}
