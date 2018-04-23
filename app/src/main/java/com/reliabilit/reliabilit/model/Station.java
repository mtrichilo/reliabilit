package com.reliabilit.reliabilit.model;

import com.reliabilit.reliabilit.json.StationJson;
import com.reliabilit.reliabilit.util.Streamer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Station {
    private String id;
    private String name;

    private Set<Route> routes;
    private Set<Station> next;
    private Set<Station> children;

    private Station() {
        this.routes = new HashSet<>();
        this.next = new HashSet<>();
        this.children = new HashSet<>();
    }

    public static Station fromJson(StationJson json) {
        Station station = new Station();
        station.id = json.getId();
        station.name = json.getName();
        return station;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Collection<Route> getRoutes() {
        return this.routes;
    }

    public Collection<Station> getNext() {
        return this.next;
    }

    public Collection<Station> getChildren() {
        return this.children;
    }

    public void addRoute(Route route) {
        this.routes.add(route);
    }

    public void addNext(Station next) {
        this.next.add(next);
    }

    public void addChild(Station child) {
        this.children.add(child);
    }

    public boolean onSameRoute(Station station) {
        return Streamer.intersect(this.routes, station.getRoutes());
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object o) {
        return o == this || o instanceof Station && ((Station) o).getId().equals(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
