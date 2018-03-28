package com.example.greg.octranspo.octranspo;

/**
 * Created by jason on 21/03/18.
 */

public class Route {

    private short routeNumber;
    private byte directionId;
    private String direction;
    private String routeHeading;

    public Route(short routeNumber, String routeHeading) {
        this.routeNumber = routeNumber;
        this.routeHeading = routeHeading;
    }

    public short getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(short routeNumber) {
        this.routeNumber = routeNumber;
    }

    public byte getDirectionId() {
        return directionId;
    }

    public void setDirectionId(byte directionId) {
        this.directionId = directionId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRouteHeading() {
        return routeHeading;
    }

    public void setRouteHeading(String routeHeading) {
        this.routeHeading = routeHeading;
    }
}
