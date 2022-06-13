package com.gpsdemo.connection;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import javafx.scene.control.TextArea;

import com.gluonhq.charm.glisten.mvc.View;
import com.gpsdemo.shared.DeviceInfo;

public final class WebSocketsConnection implements Connection {

	public WebSocketsConnection() {
		WebSocket.Listener listener = new WebSocket.Listener() {

			List<CharSequence> parts = new ArrayList<>();
			CompletableFuture<?> accumulatedMessage = new CompletableFuture<>();

			public CompletionStage<?> onText(WebSocket webSocket, CharSequence message, boolean last) {
				parts.add(message);
				webSocket.request(1);
				if (last) {
//					processWholeText(parts);
					parts = new ArrayList<>();
					accumulatedMessage.complete(null);
					CompletionStage<?> cf = accumulatedMessage;
					accumulatedMessage = new CompletableFuture<>();
					return cf;
				}
				return accumulatedMessage;
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

	@Override
	public void connect() {}

	@Override
	public void send(DeviceInfo deviceInfo) {}

	@Override
	public void close() {}
}
