package com.example.greg.octranspo.octranspo;

/**
 * Created by jason on 02/04/18.
 */

public class Trip {

    // Next trip info
    private String tripDestination;
    private String startTime;
    private String adjustScheduleTime;
    private boolean lastTrip;


    private String gpsLat, gpsLong;
    private String speed;

    public Trip() {}

    public String getTripDestination() {
        return tripDestination;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination = tripDestination;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getAdjustScheduleTime() {
        return adjustScheduleTime;
    }

    public void setAdjustScheduleTime(String adjustScheduleTime) {
        this.adjustScheduleTime = adjustScheduleTime;
    }

    public boolean isLastTrip() {
        return lastTrip;
    }

    public void setLastTrip(boolean lastTrip) {
        this.lastTrip = lastTrip;
    }

    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }

    public String getGpsLong() {
        return gpsLong;
    }

    public void setGpsLong(String gpsLong) {
        this.gpsLong = gpsLong;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
