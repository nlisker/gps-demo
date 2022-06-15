package com.gpsdemo.notification;

import javafx.scene.media.AudioClip;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import com.gluonhq.attach.audio.Audio;
import com.gluonhq.attach.audio.AudioService;
import com.gpsdemo.shared.Warning;

public abstract sealed class Notifier {

	private static Notifier INSTANCE;

	public static Notifier get() {
		return INSTANCE != null ? INSTANCE : (INSTANCE = createNotifier());
	}

	private static Notifier createNotifier() {
		return AudioService.create().<Notifier>map(MobileNotifier::new).orElseGet(DesktopNotifier::new);
	}

	private Notifier() {}

	public abstract void warn(Warning warning);

	@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
	private final static class MobileNotifier extends Notifier {

		private final Audio SHORT_BEEP;
		private final Audio LONG_BEEP;

		private MobileNotifier(AudioService service) {
			SHORT_BEEP = service.loadSound(getClass().getResource("/sounds/ShortBeep.wav")).orElseThrow();
			LONG_BEEP = service.loadSound(getClass().getResource("/sounds/LongBeep.wav")).orElseThrow();
		}

		@Override
		public void warn(Warning warning) {
			switch (warning) {
				case NONE -> {
					SHORT_BEEP.stop();
					LONG_BEEP.stop();
				}
				case ORANGE -> {
//					if (!SHORT_BEEP.isPlaying()) {
						SHORT_BEEP.play();
//					}
				}
				case RED -> {
//					if (!LONG_BEEP.isPlaying()) {
						LONG_BEEP.play();
//					}
//					if (SHORT_BEEP.isPlaying()) {
						SHORT_BEEP.stop();
//					}
				}
			};
		}
	}

	private final static class DesktopNotifier extends Notifier {

		private static final AudioClip SHORT_BEEP = new AudioClip(Notifier.class.getResource("/sounds/ShortBeep.wav").toExternalForm());
		private static final AudioClip LONG_BEEP = new AudioClip(Notifier.class.getResource("/sounds/LongBeep.wav").toExternalForm());

		private DesktopNotifier() {}

		@Override
		public void warn(Warning warning) {
			switch (warning) {
				case NONE -> {
					SHORT_BEEP.stop();
					LONG_BEEP.stop();
				}
				case ORANGE -> {
					if (!SHORT_BEEP.isPlaying()) {
						SHORT_BEEP.play();
					}
				}
				case RED -> {
					if (!LONG_BEEP.isPlaying()) {
						LONG_BEEP.play();
					}
					if (SHORT_BEEP.isPlaying()) {
						SHORT_BEEP.stop();
					}
				}
			};
		}
	}
}