package com.gpsdemo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javafx.scene.control.TextArea;

import com.gluonhq.charm.glisten.mvc.View;

public class WebSocketView extends View {

	static TextArea textArea = new TextArea();

	public WebSocketView() {
		WebSocket.Listener listener = new WebSocket.Listener() {
			@Override
			public void onOpen(WebSocket webSocket) {
				System.out.println("open");
			}
			
		};

		HttpClient client = HttpClient.newHttpClient();
		CompletableFuture<WebSocket> ws = client.newWebSocketBuilder().buildAsync(URI.create("ws://websocket.example.com"), listener);
		try {
			WebSocket webSocket = ws.get();
			webSocket.sendText("SA", true);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

//		URI uri = null;
//		try {
//			uri = new URI("ws://localhost:8887");
//		} catch (URISyntaxException e1) {
//			e1.printStackTrace();
//		}
//		WebSocketClient client = new Connection(uri);
//		client.connect();
//		Button send = new Button("Send");
//		send.setOnAction(e -> client.send("Sending"));
//
//		setCenter(textArea);
//		setBottom(send);
	}
}
