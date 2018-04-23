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
        List<PerformanceResult> results = new ArrayList<>();
        for (List<Station> segment : this.route) {
            for (int i = 0; i < segment.size(); i++) {
                Station current = segment.get(i);
                PerformanceResult result = new PerformanceResult();
                result.setStation(current);
                if (i == 0) {
                    HeadwaysJson headways = this.service.fetchHeadways(current, from, to);
                    result.setHeadway(headways.getActual(), headways.getBenchmark());
                }
                if (i + 1 < segment.size()) {
                    TravelTimesJson times =
                            this.service.fetchTravelTimes(current, segment.get(i + 1),
                                    this.from, this.to);
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
