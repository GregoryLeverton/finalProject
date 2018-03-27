package com.example.greg.octranspo.octranspo;

/**
 * Created by jason on 21/03/18.
 */

public class StopSearchResult {

    private short id;

    private String stopName;

    public StopSearchResult(short id, String stopName) {
        this.id = id;
        this.stopName = stopName;
    }

    public short getId() {
        return id;
    }

    public String getStopName() {
        return stopName;
    }

    @Override
    public boolean equals(Object a) {
        return ((StopSearchResult) a).getId() == this.getId();
    }

}
