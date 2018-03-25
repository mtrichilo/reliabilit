package com.reliabilit.reliabilit.model;

import java.util.ArrayList;
import java.util.List;

public class RailLine {
    private Line line;
    private double reliability;
    private List<Station> stations;

    public RailLine(Line line) {
        this.line = line;
        this.reliability = 98.5;
        this.stations = new ArrayList<>();
    }

    public Line getLine() {
        return this.line;
    }

    public double getReliability() {
        return this.reliability;
    }

    public List<Station> getStations() {
        return this.stations;
    }
}
