package com.gpsdemo.shared;

public record DeviceInfo(long id, long time, double lon, double lat, double alt, double ax, double ay, double az) {}