package com.gpsdemo;

import static com.gluonhq.charm.glisten.visual.MaterialDesignIcon.*;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import com.gluonhq.attach.position.Position;
import com.gluonhq.attach.position.PositionService;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.control.SettingsPane;
import com.gluonhq.charm.glisten.control.settings.DefaultOption;
import com.gluonhq.charm.glisten.mvc.View;

public class HomeView extends View {

//	private static final double VGAP = 15;

	private PositionService positionService;

//	private final BooleanProperty send = new SimpleBooleanProperty();
//	private final BooleanProperty receive = new SimpleBooleanProperty();

//	private final ObjectProperty<Position> thisPos = new SimpleObjectProperty<>();
//	private final ObjectProperty<Position> theirPos = new SimpleObjectProperty<>();
//
//	private final Circle circle = new Circle(100, Color.GREEN);
//	private final Animation circleAnim = new FillTransition(Duration.seconds(0.5), circle, Color.RED, Color.WHITE);
//
	public HomeView() {
//		var settingsPane = createSettingsPane();
//		var labelsPane = createLabelsPane();
//		var circlePane = createCirclePane();
//
//		setupCircleAnimation();
//		setupSendAndReceive();
		positioning();
//
//		var controls = new VBox(VGAP, settingsPane, labelsPane, new Separator(), circlePane);
//		setCenter(controls);
	}
//
//	private SettingsPane createSettingsPane() {
//		var sendOption = new DefaultOption<>(CLOUD_UPLOAD.graphic(), "Send", "Sends location", null, send, true);
//		var receiveOption = new DefaultOption<>(CLOUD_DOWNLOAD.graphic(), "Receive", "Receives location", null, receive, true);
//		var settingsPane = new SettingsPane();
//		settingsPane.getOptions().addAll(sendOption, receiveOption);
//		settingsPane.setSearchBoxVisible(false);
//		return settingsPane;
//	}
//
//	private VBox createLabelsPane() {
//		var font = new Font(16);
//		var insets = new Insets(0, 0, 0, 10);
//
//		var thisPosLabel = new Label();
//		var thisPosStringProp = Bindings.createStringBinding(() -> "This location: " + positionToString(thisPos.get()), thisPos);
//		thisPosLabel.textProperty().bind(thisPosStringProp);
//		thisPosLabel.setFont(font);
//		thisPosLabel.setPadding(insets);
//
//		var theirPosLabel = new Label();
//		var theirPosStringProp = Bindings.createStringBinding(() -> "Their location: " + positionToString(theirPos.get()), theirPos);
//		theirPosLabel.textProperty().bind(theirPosStringProp);
//		theirPosLabel.setFont(font);
//		theirPosLabel.setPadding(insets);
//		return new VBox(VGAP, thisPosLabel, theirPosLabel);
//	}
//
//	private String positionToString(Position pos) {
//		if (pos == null) {
//			return "";
//		}
//		return pos.getLatitude() + ", " + pos.getLongitude() + ", " + pos.getAltitude();
//	}
//
//	private HBox createCirclePane() {
//		circle.setStroke(Color.BLACK);
//		circle.setStrokeWidth(3);
//		var circlePane = new HBox(circle);
//		circlePane.setAlignment(Pos.CENTER);
//		return circlePane;
//	}

//	private void setupSendAndReceive() {
//		send.addListener((obs, ov, nv) -> {
//			if (nv) {
//				positionService.start(); // TODO: add start params as options
//				// send data
//			} else {
//				positionService.stop();
//			}
//		});
//
//		receive.addListener((obs, ov, nv) -> {
//			if (nv) {
//				circleAnim.play();
//			} else {
//				circleAnim.stop();
//			}
//		});
//	}

	private void positioning() {
		PositionService.create().ifPresent(service -> {
			positionService = service;
		});
//			thisPos.bind(service.positionProperty());
//		}, () -> thisPos.set(new Position(1, 2, 3)));
	}

//	private void setupCircleAnimation() {
//		circleAnim.setAutoReverse(true);
//		circleAnim.setCycleCount(Animation.INDEFINITE);
//		circleAnim.statusProperty().addListener((obs, ov, nv) -> {
//			if (nv == Animation.Status.STOPPED) {
//				circle.setFill(Color.GREEN);
//			}
//		});
//	}

	@Override
	protected void updateAppBar(AppBar appBar) {
		appBar.setVisible(false);
	}
}