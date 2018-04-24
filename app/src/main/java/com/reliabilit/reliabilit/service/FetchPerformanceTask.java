package com.reliabilit.reliabilit.service;

import android.os.AsyncTask;

import com.reliabilit.reliabilit.json.HeadwaysJson;
import com.reliabilit.reliabilit.json.TravelTimesJson;
import com.reliabilit.reliabilit.model.Station;

import java.util.ArrayList;
import java.util.List;

class FetchPerformanceTask extends AsyncTask<Runnable, Void, Runnable[]> {
    private PerformanceService service;
    private List<List<Station>> route;
    private long from;
    private long to;

    FetchPerformanceTask(PerformanceService service, List<List<Station>> route, long from, long to) {
        this.service = service;
        this.route = route;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Runnable[] doInBackground(Runnable... runnables) {
        // Empty list of results.
        List<PerformanceResult> results = new ArrayList<>();

        // Loop through each segment of the route. A segment represents a
        // transfer between lines.
        for (List<Station> segment : this.route) {
            for (int i = 0; i < segment.size(); i++) {

                // Get the current station and initialize the result.
                Station current = segment.get(i);
                PerformanceResult result = new PerformanceResult();
                result.setStation(current);

                // Only get headway information if this is the first station.
                if (i == 0) {
                    HeadwaysJson headways = this.service.fetchHeadways(current, from, to);
                    result.setHeadway(headways.getActual(), headways.getBenchmark());
                }

                // Otherwise, if there is a station to travel to, fetch the travel times.
                if (i + 1 < segment.size()) {
                    TravelTimesJson times =
                            this.service.fetchTravelTimes(current, segment.get(i + 1), this.from, this.to);
                    result.setTravelTime(times.getActual(), times.getBenchmark());
                }
                results.add(result);
            }
        }

        this.service.setResult(results);
        return runnables;
    }

    @Override
    protected void onPostExecute(Runnable[] runnables) {
        for (Runnable runnable : runnables) {
            runnable.run();
        }
    }
}
