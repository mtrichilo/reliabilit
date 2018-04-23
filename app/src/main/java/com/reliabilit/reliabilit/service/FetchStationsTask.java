package com.reliabilit.reliabilit.service;

import android.os.AsyncTask;

import com.reliabilit.reliabilit.json.ShapeJson;
import com.reliabilit.reliabilit.json.StationJson;
import com.reliabilit.reliabilit.model.Route;
import com.reliabilit.reliabilit.model.Station;
import com.reliabilit.reliabilit.util.Streamer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class FetchStationsTask extends AsyncTask<Runnable, Void, Runnable[]> {
    private StationService service;

    FetchStationsTask(StationService service) {
        this.service = service;
    }

    @Override
    protected Runnable[] doInBackground(Runnable... runnables) {
        List<Route> routes = new ArrayList<>();
        for (RouteType type : RouteType.subwayRoutes()) {
            routes.addAll(this.service.fetchRoutes(type).map(Route::fromJson));
        }

        List<ShapeJson> shapes = new ArrayList<>();
        Map<String, Station> stations = new HashMap<>();
        Map<String, Map<String, Station[]>> routeToChildren = new HashMap<>();
        for (Route route : routes) {
            List<ShapeJson> allShapes = this.service.fetchShapes(route.getId()).getData();
            shapes.addAll(Streamer.filter(allShapes, shape -> shape.getPriorty() > 0));
            Map<String, Station[]> children = new HashMap<>();
            routeToChildren.put(route.getId(), children);
            this.service.fetchStationsByRoute(route.getId()).foreach(parentJson -> {
                if (!stations.containsKey(parentJson.getId())) {
                    Station parent = Station.fromJson(parentJson);
                    stations.put(parentJson.getId(), parent);
                }

                Station parent = stations.get(parentJson.getId());
                for (StationJson childJson : parentJson.getChildren()) {
                    if (childJson.getId().matches("7\\d{4}")) {
                        Station child = Station.fromJson(childJson);
                        parent.addChild(child);
                        if (!children.containsKey(parent.getId())) {
                            children.put(parent.getId(), new Station[2]);
                        }
                        int childDirection = Integer.parseInt(childJson.getId()) % 2;
                        children.get(parent.getId())[childDirection] = child;
                    }
                }
                parent.addRoute(route);
            });
        }

        for (ShapeJson shape : shapes) {
            Streamer.forEach(shape.getStationIds(), (Station previous, String current) -> {
                Station child = routeToChildren.get(shape.getRouteId()).get(current)[shape.getDirection()];
                if (previous != null) {
                    previous.addNext(child);
                }
                return child;
            });
        }

        this.service.setStations(stations.values());
        return runnables;
    }

    @Override
    protected void onPostExecute(Runnable[] runnables) {
        for (Runnable runnable : runnables) {
            runnable.run();
        }
    }
}
