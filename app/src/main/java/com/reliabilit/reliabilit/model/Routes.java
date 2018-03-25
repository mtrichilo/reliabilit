package com.reliabilit.reliabilit.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Routes {
    @SerializedName("data")
    private Route[] routes;

    public Routes() {}

    public List<Route> getRoutes() {
        return Arrays.asList(routes);
    }

    public List<String> getRouteIds() {
        return Arrays.stream(this.routes).map(Route::getId).collect(Collectors.toList());
    }
}
