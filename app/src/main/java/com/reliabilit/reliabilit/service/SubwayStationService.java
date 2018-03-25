package com.reliabilit.reliabilit.service;

import com.google.gson.Gson;
import com.reliabilit.reliabilit.model.Routes;
import com.reliabilit.reliabilit.model.Stations;
import com.reliabilit.reliabilit.model.Stations.Station;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SubwayStationService implements StationService {
    public SubwayStationService() {
    }

    private Routes getRoutes(String type) throws IOException {
        Request r = new Request().addParameter("filter[type]", type);
        return r.makeRequest("/routes?", Routes.class);
    }

    private Stations getStations(String routeId) throws IOException {
        Request r = new Request().addParameter("filter[route]", routeId);
        return r.makeRequest("/stops?", Stations.class);
    }

    @Override
    public List<String> getStationNames() throws IOException {
        List<String> routeIds = getRoutes("0").getRouteIds();
        routeIds.addAll(getRoutes("1").getRouteIds());

        List<String> stationNames = new ArrayList<>();
        for (String routeId : routeIds) {
            stationNames.addAll(this.getStations(routeId).getStationNames());
        }
        return stationNames;
    }
}
