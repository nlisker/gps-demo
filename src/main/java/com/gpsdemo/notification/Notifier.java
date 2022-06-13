package com.gpsdemo.notification;

import javafx.scene.media.AudioClip;

import com.gpsdemo.shared.Warning;

public class Notifier {

	private static Notifier INSTANCE;

	public static Notifier get() {
		return INSTANCE != null ? INSTANCE : (INSTANCE = new Notifier());
	}

	private static final AudioClip SHORT_BEEP = new AudioClip(Notifier.class.getResource("/sounds/ShortBeep.wav").toExternalForm());
	private static final AudioClip LONG_BEEP = new AudioClip(Notifier.class.getResource("/sounds/LongBeep.wav").toExternalForm());

	private Notifier() {}

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