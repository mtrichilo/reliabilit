package com.reliabilit.reliabilit.service;

import com.reliabilit.reliabilit.model.Station;

public class PerformanceResult {
    private Station station;
    private int actualHeadway;
    private int benchmarkHeadway;
    private int actualTravelTime;
    private int benchmarkTravelTime;

    public Station getStation() {
        return station;
    }

    public int getActualHeadway() {
        return actualHeadway;
    }

    public int getBenchmarkHeadway() {
        return benchmarkHeadway;
    }

    public int getActualTravelTime() {
        return actualTravelTime;
    }

    public int getBenchmarkTravelTime() {
        return benchmarkTravelTime;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setHeadway(int actual, int benchmark) {
        this.actualHeadway = actual;
        this.benchmarkHeadway = benchmark;
    }

    public void setTravelTime(int actual, int benchmark) {
        this.actualTravelTime = actual;
        this.benchmarkTravelTime = benchmark;
    }
}
