package com.reliabilit.reliabilit.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.reliabilit.reliabilit.model.Station;
import com.reliabilit.reliabilit.service.PerformanceResult;
import com.reliabilit.reliabilit.service.PerformanceService;
import com.reliabilit.reliabilit.service.StationService;

import java.util.Collection;
import java.util.List;

public class Home extends AppCompatActivity {
    private Collection<Station> stations;
    private List<PerformanceResult> result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StationService stationService = new StationService();
        stationService.fetchStations(() -> this.stations = stationService.getStations());

//        Station[] s = this.stations.toArray(new Station[0]);
//        List<List<Station>> route = stationService.findRoute(s[3], s[40]);
//
//        PerformanceService perfService = new PerformanceService();
//        perfService.fetchPerformance(route, 1524528000000L, 1524531600000L, () ->
//                this.result = perfService.getResult());
//
//        int i = 4 + 4;
    }
}
