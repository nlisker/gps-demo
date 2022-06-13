package com.gpsdemo;

import javafx.application.Application;

public class Launcher {

	public static void main(String[] args) {
//    	System.setProperty(com.gluonhq.attach.util.Constants.ATTACH_DEBUG, "true");
        Application.launch(GPSApplication.class, args);
	}
}
