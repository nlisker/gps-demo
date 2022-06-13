package com.gpsdemo.view;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gpsDemo.shared.Warning;
import com.gpsdemo.EventsHandler;
import com.gpsdemo.GPSApplication;
import com.gpsdemo.notification.Notifier;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DebugView extends View {

	private static DebugView INSTANCE;

	public static DebugView get() {
		return INSTANCE != null ? INSTANCE : (INSTANCE = new DebugView());
	}

	TextArea console = new TextArea();

	private DebugView() {
		console.setEditable(false);
		console.setWrapText(true);
		setCenter(console);

		var startButton = new Button("Start");
		startButton.setOnAction(e -> EventsHandler.get().start());

		var stopButton = new Button("Stop");
		stopButton.setOnAction(e -> EventsHandler.get().stop());

		var clearButton = new Button("Clear");
		clearButton.setOnAction(e -> console.clear());

		var longBeepButton = new Button("Red warning");
		longBeepButton.setOnAction(e -> Notifier.get().warn(Warning.RED));
		var shortBeepButton = new Button("Orange warning");
		shortBeepButton.setOnAction(e -> Notifier.get().warn(Warning.ORANGE));

		setBottom(new HBox(5, startButton, stopButton, clearButton, longBeepButton, shortBeepButton));
	}

	public void log(String message) {
		console.appendText(message + "\n");
	}

	@Override
	protected void updateAppBar(AppBar appBar) {
		appBar.setTitleText("Debug Console");

		var optionsButton = MaterialDesignIcon.SETTINGS.button(e -> getAppManager().switchView(GPSApplication.OPTIONS_VIEW));
		appBar.getActionItems().add(optionsButton);
	}
}