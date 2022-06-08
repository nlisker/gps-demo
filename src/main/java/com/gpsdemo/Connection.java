package com.gpsdemo;


//
//public class Connection extends WebSocketClient {
//
//	public Connection(URI serverUri, Draft draft) {
//		super(serverUri, draft);
//	}
//
//	public Connection(URI serverURI) {
//		super(serverURI);
//	}
//
//	@Override
//	public void onOpen(ServerHandshake handshakedata) {
//		send("Hello, it is me. Mario :)");
//		System.out.println("new connection opened");
//		WebSocketView.textArea.appendText("new connection opened");
//	}
//
//	@Override
//	public void onClose(int code, String reason, boolean remote) {
//		System.out.println("closed with exit code " + code + " additional info: " + reason);
//		WebSocketView.textArea.appendText("closed with exit code " + code + " additional info: " + reason);
//	}
//
//	@Override
//	public void onMessage(String message) {
//		System.out.println("received message: " + message);
//		WebSocketView.textArea.appendText("received message: " + message);
//	}
//
//	@Override
//	public void onMessage(ByteBuffer message) {
//		System.out.println("received ByteBuffer");
//		WebSocketView.textArea.appendText("received ByteBuffer");
//	}
//
//	@Override
//	public void onError(Exception ex) {
//		System.err.println("an error occurred:" + ex);
//		WebSocketView.textArea.appendText("an error occurred:" + ex);
//	}
//}