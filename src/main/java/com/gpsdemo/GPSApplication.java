package com.gpsdemo;

import static com.gluonhq.charm.glisten.application.AppManager.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import com.gluonhq.charm.glisten.application.AppManager;

public class GPSApplication extends Application {

    private final AppManager appManager = AppManager.initialize(this::postInit);

    @Override
    public void init() {
    	appManager.addViewFactory(HOME_VIEW, WebSocketView::new);
    }

	@Override
	public void start(Stage stage) throws Exception {
		appManager.start(stage);
//		if (com.gluonhq.attach.util.Platform.isDesktop()) {
//			stage.setHeight(600);
//			stage.setWidth(360);
//			stage.centerOnScreen();
//		}
	}

	private void postInit(Scene scene) {
		Platform.runLater(() -> ((Button) appManager.getGlassPane().lookup(".button.flat.light")).fire());
	}

    public static void main(String args[]) {
    	System.setProperty(com.gluonhq.attach.util.Constants.ATTACH_DEBUG, "true");
        launch(args);
    }
}
