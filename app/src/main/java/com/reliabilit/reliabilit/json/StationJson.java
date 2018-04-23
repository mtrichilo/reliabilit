package com.reliabilit.reliabilit.json;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StationJson implements Json {
    @SerializedName("id")
    private String id;
    @SerializedName("attributes")
    private Attributes attributes;
    @SerializedName("relationships")
    private Relationships relationships;

    public Type getTypeToken() {
        return new TypeToken<Data<StationJson>>(){}.getType();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        if (this.attributes == null) {
            return null;
        }
        return this.attributes.name;
    }

    public List<StationJson> getChildren() {
        if (this.relationships.childStops == null) {
            return new ArrayList<>();
        }
        return this.relationships.childStops.getData();
    }

    private class Attributes {
        @SerializedName("name")
        private String name;
    }

    private class Relationships {
        @SerializedName("child_stops")
        private Data<StationJson> childStops;
    }
}
