package com.gpsdemo.telemetry;

import java.util.concurrent.ThreadLocalRandom;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import com.gluonhq.attach.accelerometer.Acceleration;
import com.gluonhq.attach.accelerometer.AccelerometerService;
import com.gluonhq.attach.position.Position;
import com.gluonhq.attach.position.PositionService;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelemetryReader {

	private static TelemetryReader INSTANCE;

	public static TelemetryReader get() {
		return INSTANCE != null ? INSTANCE : (INSTANCE = new TelemetryReader());
	}

	PositionService positionService;
	AccelerometerService accelerometerService;

	public ReadOnlyObjectProperty<Position> positionProperty() {
		return positionService.positionProperty();
	}

	public ReadOnlyObjectProperty<Acceleration> accelerationProperty() {
		return accelerometerService.accelerationProperty();
	}

	private final float posDistanceFilter = 0;

	LongProperty posSampleInterval = new SimpleLongProperty(200); // ms
	public LongProperty posSampleIntervalProperty() {
		return posSampleInterval;
	}

	LongProperty accelFrequency = new SimpleLongProperty(50); // Hz for accel
	public LongProperty accelFrequencyProperty() {
		return accelFrequency;
	}

	BooleanProperty gravityFilter = new SimpleBooleanProperty();
	public BooleanProperty gravityFilterProperty() {
		return gravityFilter;
	}

	private TelemetryReader() {
		positionService = PositionService.create().orElseGet(MockPositionService::new);
		accelerometerService = AccelerometerService.create().orElseGet(MockAccelerometerService::new);
	}

	public void start() {
		positionService.start(new com.gluonhq.attach.position.Parameters(null, posSampleInterval.get(), posDistanceFilter, true));
		accelerometerService.start(new com.gluonhq.attach.accelerometer.Parameters(accelFrequency.get(), gravityFilter.get()));
	}

	public void stop() {
		positionService.stop();
		accelerometerService.stop();
	}

	/**
	 * Every 100ms the car moves 1 unit distance. Starting at (100, 100).
	 */
	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	private static class MockPositionService implements PositionService {

		@NonFinal
		long lastTime = System.currentTimeMillis();

		ScheduledService<Void> svc = new ScheduledService<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() {
						updatePosition();
						return null;
					}
				};
			}
		};

		ObjectProperty<Position> position = new SimpleObjectProperty<>(new Position(100, 100));

		@Override
		public ReadOnlyObjectProperty<Position> positionProperty() {
			return position;
		}

		@Override
		public Position getPosition() {
			return positionProperty().get();
		}

		@Override
		public void start(com.gluonhq.attach.position.Parameters parameters) {
			stop();
			long timeInterval = parameters.getTimeInterval();
			svc.setPeriod(Duration.millis(timeInterval));
			svc.restart();
		}

		@Override
		public void start() {
			start(new com.gluonhq.attach.position.Parameters(null, 500, 0, true));
		}

		@Override
		public void stop() {
			svc.cancel();
		}

		private void updatePosition() {
			long currentTime = System.currentTimeMillis();
			long timeEllapsed = currentTime - lastTime;
			lastTime = currentTime;
			double lat = getPosition().getLatitude() - timeEllapsed / 100d;
			double lon = getPosition().getLongitude() - timeEllapsed / 100d;
//			DebugView.get().log("   l0=" + getPosition().getLatitude() + "; dT=" + timeEllapsed + "; d1=" + lat);
			position.set(new Position(lat, lon));
//			Platform.runLater(() -> position.set(new Position(lat, lon)));
		}
	}

	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	private static class MockAccelerometerService implements AccelerometerService {

		ScheduledService<Void> svc = new ScheduledService<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() {
						updateAcceleration();
						return null;
					}
				};
			}
		};

		ObjectProperty<Acceleration> acceleration = new SimpleObjectProperty<>(new Acceleration(0, 0, 0, null));

		@Override
		public ReadOnlyObjectProperty<Acceleration> accelerationProperty() {
			return acceleration;
		}

		@Override
		public Acceleration getCurrentAcceleration() {
			return accelerationProperty().get();
		}

		@Override
		public void start(com.gluonhq.attach.accelerometer.Parameters parameters) {
			stop();
			double frequency = parameters.getFrequency();
			long timeInterval = Math.round(1000 / frequency);
			svc.setPeriod(Duration.millis(timeInterval));
			svc.restart();
		}

		@Override
		public void start() {
			start(new com.gluonhq.attach.accelerometer.Parameters(500, true));
		}

		@Override
		public void stop() {
			svc.cancel();
		}

		private void updateAcceleration() {
			var random = ThreadLocalRandom.current();
			double ax = random.nextDouble(-5, 5);
			double ay = random.nextDouble(-5, 5);
			acceleration.set(new Acceleration(ax, ay, 0, null));
//			Platform.runLater(() -> acceleration.set(new Acceleration(ax, ay, 0, null)));
		}
	}
}