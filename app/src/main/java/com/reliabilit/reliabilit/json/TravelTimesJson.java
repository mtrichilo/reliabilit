package com.reliabilit.reliabilit.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TravelTimesJson {
    @SerializedName("travel_times")
    private List<Time> times;

    public int getActual() {
        if (this.times.size() == 0) {
            return 0;
        }

        int totalTime = 0;
        for (Time t : this.times) {
            totalTime += t.getActualTime();
        }
        return totalTime / this.times.size();
    }

    public int getBenchmark() {
        if (this.times.size() == 0) {
            return 0;
        }

        int totalTime = 0;
        for (Time t : this.times) {
            totalTime += t.getBenchmarkTime();
        }
        return totalTime / this.times.size();
    }

    public int getSize() {
        return this.times.size();
    }

    private class Time {
        @SerializedName("route_id")
        private String routeId;
        @SerializedName("direction")
        private String direction;
        @SerializedName("travel_time_sec")
        private String actualTime;
        @SerializedName("benchmark_travel_time_sec")
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
