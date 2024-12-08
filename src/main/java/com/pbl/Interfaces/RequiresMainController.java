package com.pbl.Interfaces;

import com.pbl.viewController.NavigatorController;
import com.pbl.controller.mainController;

/**
 * Interface responsável por exigir que a classe que a implementa tenha um método para definir as dependências do controlador principal.
 * Esta interface é útil para classes que precisam interagir com o controlador principal e o controlador de navegação.
 */
public interface RequiresMainController {

    /**
     * Método utilizado para associar as dependências dos controladores à classe que implementa esta interface.
     *
     * @param mainController O controlador principal da aplicação.
     * @param navigatorController O controlador responsável pela navegação entre as telas.
     */
    void setDependencies(mainController mainController, NavigatorController navigatorController);
}
