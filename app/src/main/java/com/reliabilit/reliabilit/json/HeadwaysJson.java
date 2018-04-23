package com.reliabilit.reliabilit.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeadwaysJson {
    @SerializedName("headways")
    private List<Headway> headways;

    public int getActual() {
        if (this.headways.size() == 0) {
            return 0;
        }

        int totalTime = 0;
        for (Headway h : this.headways) {
            totalTime += h.getActualTime();
        }
        return totalTime / this.headways.size();
    }

    public int getBenchmark() {
        if (this.headways.size() == 0) {
            return 0;
        }

        int totalTime = 0;
        for (Headway h : this.headways) {
            totalTime += h.getBenchmarkTime();
        }
        return totalTime / this.headways.size();
    }

    public int getSize() {
        return this.headways.size();
    }

    private class Headway {
        @SerializedName("route_id")
        private String routeId;
        @SerializedName("prev_route_id")
        private String previousRouteId;
        @SerializedName("direction")
        private String direction;
        @SerializedName("current_dep_dt")
        private String currentDepartureTime;
        @SerializedName("previous_dep_dt")
        private String previousDepartureTime;
        @SerializedName("headway_time_sec")
        private String actualTime;
        @SerializedName("benchmark_headway_time_sec")
        private String benchmarkTime;
        @SerializedName("threshold_flag_1")
        private String threshold1;
        @SerializedName("threshold_flag_2")
        private String threshold2;
        @SerializedName("threshold_flag_3")
        private String threshold3;

        int getActualTime() {
            return Integer.parseInt(this.actualTime);
        }

        int getBenchmarkTime() {
            return Integer.parseInt(this.benchmarkTime);
        }
    }
}
