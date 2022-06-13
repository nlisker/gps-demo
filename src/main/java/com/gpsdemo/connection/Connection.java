package com.gpsdemo.connection;

import com.gpsDemo.shared.DeviceInfo;

public sealed interface Connection permits WebSocketsConnection, RESTConnection {

	enum ConnectionType {
		NONE,
		REST,
		WEB_SOCKETS;
	}

	static Connection createConnection(ConnectionType connectionType) {
		return switch (connectionType) {
			case NONE -> null;
			case REST -> new RESTConnection();
			case WEB_SOCKETS -> new WebSocketsConnection();
		};
	}

	void connect();

	void send(DeviceInfo deviceInfo);

	void close();
}