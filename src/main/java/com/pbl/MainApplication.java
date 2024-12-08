package com.pbl;

import com.pbl.Factory.ControllerFactory;
import com.pbl.controller.mainController;
import com.pbl.service.*;  // Importe todos os serviços necessários
import com.pbl.Stores.*; // Importando todas as Stores necessárias
import com.pbl.viewController.NavigatorController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Classe principal da aplicação. Responsável por iniciar a aplicação, configurar a janela principal e
 * exibir a tela de login.
 */
public class MainApplication extends Application {

    /**
     * Método de inicialização da aplicação, responsável por configurar o estágio principal (janela),
     * adicionar o ícone da janela e mostrar a tela de login.
     *
     * @param stage A janela principal da aplicação.
     * @throws Exception Se ocorrer algum erro durante a configuração da aplicação.
     */
    @Override
    public void start(Stage stage) throws Exception {

        ControllerFactory factory = new ControllerFactory();

        mainController mainController = factory.createController();

        Image iconImage = new Image(getClass().getResource("/fxml/images/icon.jpg").toExternalForm());

        stage.getIcons().add(iconImage);

        NavigatorController navigatorController = new NavigatorController(stage, mainController);

        navigatorController.showLogin();
    }

    /**
     * Método principal da aplicação, que inicia a execução da aplicação JavaFX.
     *
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
