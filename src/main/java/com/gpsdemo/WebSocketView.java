package com.gpsdemo;

import java.net.URI;
import java.net.URISyntaxException;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import org.java_websocket.client.WebSocketClient;

import com.gluonhq.charm.glisten.mvc.View;

public class WebSocketView extends View {

	static TextArea textArea = new TextArea();

	public WebSocketView() {
		URI uri = null;
		try {
			uri = new URI("ws://localhost:8887");
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		WebSocketClient client = new Connection(uri);
		client.connect();
		Button send = new Button("Send");
		send.setOnAction(e -> client.send("Sending"));

		setCenter(textArea);
		setBottom(send);
	}
}
