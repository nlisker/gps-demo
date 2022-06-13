package com.gpsdemo;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import com.gpsDemo.shared.DeviceInfo;
import com.gpsdemo.connection.Connection;
import com.gpsdemo.connection.Connection.ConnectionType;
import com.gpsdemo.telemetry.TelemetryReader;
import com.gpsdemo.view.DebugView;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventsHandler {

	private static EventsHandler INSTANCE;

	public static EventsHandler get() {
		return INSTANCE != null ? INSTANCE : (INSTANCE = new EventsHandler());
	}

	DebugView logger = DebugView.get();
	TelemetryReader telemetryReader = TelemetryReader.get();

	public ObjectProperty<ConnectionType> connectionType = new SimpleObjectProperty<>(ConnectionType.NONE);

	@NonFinal
	Connection connection = Connection.createConnection(ConnectionType.NONE);

	private EventsHandler() {
		connectionType.addListener((obs, ov, nv) -> {
			logger.log("Changing connection from " + ov + " to " + nv);
			stop();
			connection = Connection.createConnection(nv);
			start();
		});
		
		var updatingProperty = telemetryReader.positionProperty(); // assumes that position updates less frequently
		updatingProperty.addListener((obs, ov, pos) -> {
			var accel = telemetryReader.accelerationProperty().get();
			var deviceInfo = new DeviceInfo(52, System.currentTimeMillis(), pos.getLongitude(), pos.getLatitude(), pos.getAltitude(),
					accel.getX(), accel.getY(), accel.getZ());
			if (connection != null) {
				connection.send(deviceInfo);
			}
			logger.log("Sent " + deviceInfo);
		});
	}

	public void start() {
		logger.log("Starting telemetry...");
		telemetryReader.start();
	}

	public void stop() {
		logger.log("Stopping telemetry...");
		telemetryReader.stop();
	}
}