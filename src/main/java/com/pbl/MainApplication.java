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

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ControllerFactory factory = new ControllerFactory();

        mainController mainController = factory.createController();

        Image iconImage = new Image(getClass().getResource("/fxml/images/icon.jpg").toExternalForm());

        stage.getIcons().add(iconImage);

        NavigatorController navigatorController = new NavigatorController(stage, mainController);

        navigatorController.showLogin();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
