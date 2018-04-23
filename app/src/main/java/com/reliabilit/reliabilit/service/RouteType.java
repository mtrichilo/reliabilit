package com.reliabilit.reliabilit.service;

import java.util.Arrays;
import java.util.Collection;

enum RouteType {
    LIGHT_RAIL(0),
    SUBWAY(1),
    RAIL(2),
    BUS(3),
    FERRY(4),
    CABLE_CAR(5),
    GONDOLA(6),
    FUNICULAR(7);

    private int type;

    RouteType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return Integer.toString(type);
    }

    public static Collection<RouteType> subwayRoutes() {
        return Arrays.asList(LIGHT_RAIL, SUBWAY);
    }
}
