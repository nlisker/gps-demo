package com.gpsdemo.connection;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import javafx.concurrent.ScheduledService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import com.gpsdemo.notification.Notifier;
import com.gpsdemo.shared.DeviceInfo;
import com.gpsdemo.shared.RESTPaths;
import com.gpsdemo.view.DebugView;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class RESTConnection implements Connection {

	private static final String LOCAL_IP = "localhost";
	private static final String REMOTE_IP = "??.???.??.???";
	private static final int SERVER_PORT = 8080;
//	private static final int SERVER_PORT = 8443;
	private static final String SERVER_NAME = "GPSDemoServer";

	DebugView logger = DebugView.get();
	Notifier notifier = Notifier.get();

//	RestClient updateClient = RestClient.create().path(RESTPaths.UPDATE).method("GET");
//	RestClient warningClientClient = RestClient.create().method("GET");

	HttpClient client;
//	HttpRequest baseRequest;

	@NonFinal
	ScheduledService<Void> warningListener;

	@NonFinal
	long id;

	public String getAddress(boolean local) {
		String IP = local ? LOCAL_IP : REMOTE_IP;
//		updateClient.host("http://" + IP + ":" + SERVER_PORT + "/" + SERVER_NAME);
//		warningClientClient.host("http://" + IP + ":" + SERVER_PORT + "/" + SERVER_NAME);
		String address = "http://" + IP + ":" + SERVER_PORT + "/" + SERVER_NAME + "/";
		logger.log("Address: " + address);
		return address;
	}

	RESTConnection() {
		client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).followRedirects(Redirect.NORMAL).build();
//		baseRequest = HttpRequest.newBuilder()
//				.uri(URI.create(getAddress(true)))
//				.timeout(Duration.ofMinutes(2))
//				.header("Content-Type", "application/json")




//		setLocal(true);
		connect();
	}

	@Override
	public void connect() {
		HttpRequest registerRequest = HttpRequest.newBuilder().uri(URI.create(getAddress(true) + RESTPaths.REGISTER))
				  .timeout(Duration.ofSeconds(20))
				  .build();
		System.out.println(registerRequest.uri());
		HttpResponse<String> response;
		try {
			logger.log("Requesting id...");
			response = client.send(registerRequest, BodyHandlers.ofString());
			logger.log("Response status: " + response.statusCode());
			id = Long.valueOf(response.body());
			logger.log("Received id: " + id);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
//		warningClientClient.path(RESTPaths.REGISTER);
		
//		id = DataProvider.retrieveObject(warningClientClient.createObjectDataReader(Long.class)).get();
		

//		warningClientClient.path(RESTPaths.WAIT_FOR_WARNING);
//		var warningListener = new ScheduledService<Void>() {
//			@Override
//			protected Task<Void> createTask() {
//				return new Task<Void>() {
//					@Override
//					protected Void call() {
//						logger.log("Waiting for warning...");
//						Warning warning = DataProvider.retrieveObject(warningClientClient.createObjectDataReader(Warning.class)).get();
//						logger.log("Received warning: " + warning);
//						notifier.warn(warning);
//						return null;
//					}
//				};
//			}
//		};
//		warningListener.setRestartOnFailure(true);
//		logger.log("Starting warning listening...");
//		warningListener.start();
	}

	@Override
	public void send(DeviceInfo deviceInfo) {
//		DataProvider.retrieveObject(updateClient.createObjectDataReader(Void.class));
	}

	@Override
	public void close() {
		logger.log("Closing REST connection...");
		warningListener.cancel();
		logger.log("Closed REST connection");
	}
}