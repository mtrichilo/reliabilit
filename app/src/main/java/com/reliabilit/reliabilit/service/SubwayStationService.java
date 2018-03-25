package com.reliabilit.reliabilit.service;

import com.reliabilit.reliabilit.model.Data;
import com.reliabilit.reliabilit.model.Route;
import com.reliabilit.reliabilit.model.Station;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubwayStationService implements StationService {
    public SubwayStationService() {
    }

    private Data<Route> getRoutes(String type) throws IOException {
        Request r = new Request().addParameter("filter[type]", type);
        return r.makeRequest("/routes?", Route.class);
    }

    private Data<Station> getStations(String routeId) throws IOException {
        Request r = new Request().addParameter("filter[route]", routeId);
        return r.makeRequest("/stops?", Station.class);
    }

    @Override
    public List<String> getStationNames() throws IOException {
        List<String> routeIds = getRoutes("0").map(Route::getId);
        routeIds.addAll(getRoutes("1").map(Route::getId));

        List<String> stationNames = new ArrayList<>();
        for (String routeId : routeIds) {
            stationNames.addAll(this.getStations(routeId).map(Station::getName));
        }
        return stationNames;
    }
}
