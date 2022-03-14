package com.gpsdemo;

import static com.gluonhq.charm.glisten.application.AppManager.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.gluonhq.charm.glisten.application.AppManager;

public class GPSApplication extends Application {

    private final AppManager appManager = AppManager.initialize(this::postInit);

    @Override
    public void init() {
        appManager.addViewFactory(HOME_VIEW, HomeView::new);
    }

    @Override
    public void start(Stage stage) throws Exception {
        appManager.start(stage);
//        stage.setHeight(600);
//       stage.setWidth(360);
//       stage.centerOnScreen();
    }

    private void postInit(Scene scene) {}

    public static void main(String args[]) {
        launch(args);
    }
}
