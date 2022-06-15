package com.gpsdemo.view;

import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.SettingsPane;
import com.gluonhq.charm.glisten.control.settings.DefaultOption;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gpsdemo.EventsHandler;
import com.gpsdemo.telemetry.TelemetryReader;

public class OptionsView extends View {

	private static OptionsView INSTANCE;

	public static OptionsView get() {
		return INSTANCE != null ? INSTANCE : (INSTANCE = new OptionsView());
	}

	private static final TelemetryReader TELEMETRY_READER = TelemetryReader.get();

	private OptionsView() {
		var settingsPane = createSettingsPane();
		setCenter(settingsPane);
		setOnShown(e -> {
			settingsPane.applyCss();
			Set<Node> rightBox = settingsPane.lookupAll(".secondary-graphic");
			for (Node node : rightBox) {
				if (node instanceof HBox hbox) {
					hbox.prefWidthProperty().bind(hbox.minWidthProperty());
					hbox.maxWidthProperty().bind(hbox.minWidthProperty());
				}
			}
		});
	}

	private SettingsPane createSettingsPane() {
		var posSampleInterval = TELEMETRY_READER.posSampleIntervalProperty();
		var intervalOption = new DefaultOption<>("Position sample interval", "ms", "Telemetry",	posSampleInterval, true);

		var accelFrequency = TELEMETRY_READER.accelFrequencyProperty();
		var frequencyOption = new DefaultOption<>("Acceleration sample frequency", "Hz", "Telemetry", accelFrequency, true);

		var gravityFilter = TELEMETRY_READER.gravityFilterProperty();
		var gravityFilterOption = new DefaultOption<>("Gravity filter", "Filters gravity from the acceleration reading", "Telemetry",
				gravityFilter, true);

		var connectionType = EventsHandler.get().connectionType;
		var connectionTypeOption = new DefaultOption<>("Connection type", "The type of connection", "Connection",
				connectionType, true);

		var settingsPane = new SettingsPane();
		settingsPane.getOptions().addAll(intervalOption, frequencyOption, gravityFilterOption, connectionTypeOption);
		settingsPane.setSearchBoxVisible(false);
		return settingsPane;
	}

	@Override
	protected void updateAppBar(AppBar appBar) {
		appBar.setTitleText("Options");

		var backButton = MaterialDesignIcon.ARROW_BACK.button(e -> getAppManager().switchToPreviousView().get());
		appBar.setNavIcon(backButton);

		var applyButton = new Button("Apply", MaterialDesignIcon.CHECK.button());
		applyButton.setOnAction(e -> TELEMETRY_READER.start());
		appBar.getActionItems().add(applyButton);
	}
}