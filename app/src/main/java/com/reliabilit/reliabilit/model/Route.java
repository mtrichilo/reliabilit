package com.reliabilit.reliabilit.model;

import com.reliabilit.reliabilit.json.RouteJson;

public class Route {
    private String id;
    private String name;
    private String color;

    private Route() { }

    public static Route fromJson(RouteJson json) {
        Route route = new Route();
        route.id = json.getId();
        route.name = json.getName();
        route.color = json.getColor();
        return route;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getColor() {
        return this.color;
    }
}
