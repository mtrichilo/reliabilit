package com.reliabilit.reliabilit.service;

import com.reliabilit.reliabilit.json.Data;
import com.reliabilit.reliabilit.json.RouteJson;
import com.reliabilit.reliabilit.json.ShapeJson;
import com.reliabilit.reliabilit.json.StationJson;
import com.reliabilit.reliabilit.model.Station;
import com.reliabilit.reliabilit.util.Streamer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StationService {
    private Collection<Station> stations;

    /**
     * Fetches the list of route ids for the given route
     * type from the MBTA.
     * @param type Type of transport route.
     * @return Route ids (e.g. "Red", "Green-E", etc.).
     */
    Data<RouteJson> fetchRoutes(RouteType type) {
        Request r = new V3Request().addParameter("filter[type]", type);
        return r.makeDataRequest("/routes?", RouteJson.class);
    }

    /**
     * Fetches the list of shapes from the MBTA.
     * @param routeId A route to fetch shapes for.
     * @return Shapes (e.g. "Ashmont", "Lechmere", etc.).
     */
    Data<ShapeJson> fetchShapes(String routeId) {
        Request r = new V3Request().addParameter("filter[route]", routeId);
        return r.makeDataRequest("/shapes?", ShapeJson.class);
    }

    /**
     * Fetches a list of parent stations for the given route
     * id from the MBTA.
     * @param routeId A route to fetch stations for.
     * @return Stations (e.g. "Davis", "Prudential", etc.).
     */
    Data<StationJson> fetchStationsByRoute(String routeId) {
        Request r = new V3Request()
                .addParameter("filter[route]", routeId)
                .addParameter("include", "child_stops");
        return r.makeDataRequest("/stops?", StationJson.class);
    }

    void setStations(Collection<Station> stations) {
        this.stations = stations;
    }

    public void fetchStations(Runnable postExecute) {
        new FetchStationsTask(this).execute(new Runnable[] {postExecute});
    }

    public Collection<Station> getStations() {
        return this.stations;
    }

    public List<List<Station>> findRoute(Station origin, Station destination) {
        List<List<Station>> route = new ArrayList<>();
        if (origin.onSameRoute(destination)) {
            route.add(directRoute(origin, destination));
        } else {
            List<Station> transfers = Streamer.filter(this.stations, station ->
                station.onSameRoute(origin) && station.onSameRoute(destination));
            List<Station> first = new ArrayList<>();
            List<Station> second = new ArrayList<>();
            int routeLength = Integer.MAX_VALUE;
            for (Station transfer : transfers) {
                List<Station> firstTemp = directRoute(origin, transfer);
                List<Station> secondTemp = directRoute(transfer, destination);
                if (firstTemp.size() + secondTemp.size() < routeLength) {
                    first = firstTemp;
                    second = secondTemp;
                    routeLength = first.size() + second.size();
                }
            }
            route.add(first);
            route.add(second);
        }
        return route;
    }

    private List<Station> directRoute(Station origin, Station destination) {
        for (Station child : origin.getChildren()) {
            List<Station> route = traverseRoute(child, destination, new ArrayList<>());
            if (!route.isEmpty()) {
                return route;
            }
        }
        return new ArrayList<>();
    }

    private List<Station> traverseRoute(Station origin, Station destination, List<Station> route) {
        route.add(origin);
        if (destination.getChildren().contains(origin)) {
            return route;
        } else {
            for (Station next : origin.getNext()) {
                List<Station> result = traverseRoute(next, destination, new ArrayList<>(route));
                if (!result.isEmpty()) {
                    return result;
                }
            }
        }
        return new ArrayList<>();
    }
}
