package com.reliabilit.reliabilit.service;

import com.reliabilit.reliabilit.json.HeadwaysJson;
import com.reliabilit.reliabilit.json.TravelTimesJson;
import com.reliabilit.reliabilit.model.Station;

import java.util.List;

public class PerformanceService {
    private List<PerformanceResult> result;

    public void fetchPerformance(List<List<Station>> route, long from, long to,
                                 Runnable postExecute) {
        new FetchPerformanceTask(this, route, from, to).execute(new Runnable[] {postExecute});
    }

    TravelTimesJson fetchTravelTimes(Station origin, Station destination, long from, long to) {
        Request r = new V2Request()
                .addParameter("from_stop", origin.getId())
                .addParameter("to_stop", destination.getId())
                .addParameter("from_datetime", Long.toString(from))
                .addParameter("to_datetime", Long.toString(to));
        return r.makeRequest("/traveltimes?", TravelTimesJson.class);
    }

    HeadwaysJson fetchHeadways(Station station, long from, long to) {
        Request r = new V2Request()
                .addParameter("stop", station.getId())
                .addParameter("from_datetime", Long.toString(from))
                .addParameter("to_datetime", Long.toString(to));
        return r.makeRequest("/headways?", HeadwaysJson.class);
    }

    void setResult(List<PerformanceResult> result) {
        this.result = result;
    }

    public List<PerformanceResult> getResult() {
        return this.result;
    }
}
