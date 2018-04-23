package com.reliabilit.reliabilit.json;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ShapeJson implements Json {
    @SerializedName("attributes")
    private Attributes attributes;
    @SerializedName("relationships")
    private Relationships relationships;

    @Override
    public Type getTypeToken() {
        return new TypeToken<Data<ShapeJson>>(){}.getType();
    }

    public String getName() {
        return this.attributes.name;
    }

    public int getDirection() {
        return this.attributes.direction;
    }

    public int getPriorty() {
        return this.attributes.priority;
    }

    public String getRouteId() {
        return this.relationships.route.data.id;
    }

    /**
     * Returns an ordered list of station ids.
     * @return A list of station ids.
     */
    public List<String> getStationIds() {
        return this.relationships.getStationIds();
    }

    private class Attributes {
        @SerializedName("name")
        private String name;
        @SerializedName("direction_id")
        private int direction;
        @SerializedName("priority")
        private int priority;
    }

    private class Relationships {
        @SerializedName("route")
        private RouteJson route;
        @SerializedName("stops")
        private Data<StationJson> stations;

        /**
         * Returns an ordered list of station ids.
         * @return A list of station ids.
         */
        private List<String> getStationIds() {
            return this.stations.map(StationJson::getId);
        }

        private class RouteJson {
            @SerializedName("data")
            private Data data;

            private class Data {
                @SerializedName("id")
                private String id;
            }
        }
    }
}
