package com.gpsdemo;

import static com.gluonhq.charm.glisten.application.AppManager.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import com.gluonhq.charm.glisten.application.AppManager;
import com.gpsdemo.view.DebugView;
import com.gpsdemo.view.OptionsView;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GPSApplication extends Application {

	AppManager appManager = AppManager.initialize(this::postInit);

	public static final String OPTIONS_VIEW = HOME_VIEW;
	public static final String DEBUG_VIEW = "Debug View";
//	public static final String OPTIONS_VIEW = "Options View";

	@Override
	public void init() {
		appManager.addViewFactory(DEBUG_VIEW, DebugView::get);
		appManager.addViewFactory(OPTIONS_VIEW, OptionsView::get);
//		appManager.addViewFactory(SPLASH_VIEW, SplashView::new);
	}

	@Override
	public void start(Stage stage) throws Exception {
		appManager.start(stage);
		if (com.gluonhq.attach.util.Platform.isDesktop()) {
			stage.setHeight(600);
			stage.setWidth(360);
			stage.centerOnScreen();
		}
		appManager.switchView(DEBUG_VIEW);
	}

	private void postInit(Scene scene) {
		scene.getStylesheets().add(this.getClass().getResource("/css/style.css").toExternalForm());
		Platform.runLater(() -> ((Button) appManager.getGlassPane().lookup(".button.flat.light")).fire());
	}

	public static void main(String args[]) {
//		System.setProperty(com.gluonhq.attach.util.Constants.ATTACH_DEBUG, "true");
		launch(args);
	}
}
