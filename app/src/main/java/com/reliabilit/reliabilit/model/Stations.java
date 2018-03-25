package com.reliabilit.reliabilit.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Stations {
    @SerializedName("data")
    private Station[] stations;

    public List<Station> getStations() {
        return Arrays.asList(this.stations);
    }

    public List<String> getStationNames() {
        return Arrays.stream(this.stations).map(Station::getName).collect(Collectors.toList());
    }
}
