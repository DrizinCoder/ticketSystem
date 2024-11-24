package com.pbl.Interfaces;

import com.pbl.viewController.NavigatorController;
import com.pbl.controller.mainController;

public interface RequiresMainController {
    void setDependencies(mainController mainController, NavigatorController navigatorController);
}