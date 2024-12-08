package com.pbl.Interfaces;

import java.util.Locale;

/**
 * Interface responsável por gerenciar mudanças de idioma e de fonte em componentes de interface do usuário.
 * As classes que implementam esta interface devem definir o comportamento quando o idioma ou a fonte for alterada.
 */
public interface LanguageChange {

    /**
     * Método chamado para notificar que o idioma da aplicação foi alterado.
     *
     * @param currentLocale O novo idioma/locale definido para a aplicação.
     */
    void onLocaleChange(Locale currentLocale);

    /**
     * Método chamado para notificar que o estilo da fonte foi alternado.
     * As classes que implementam essa interface devem ajustar a exibição das fontes conforme necessário.
     */
    void onLocalToggleFont();
}
