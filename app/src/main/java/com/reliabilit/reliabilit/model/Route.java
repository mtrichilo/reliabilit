package com.reliabilit.reliabilit.model;

import com.google.gson.annotations.SerializedName;

public class Route {
    @SerializedName("id")
    private String id;
    @SerializedName("attributes")
    private Attributes attributes;

    private Route() {}

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.attributes.name;
    }

    public String getColor() {
        return this.attributes.color;
    }

    private class Attributes {
        @SerializedName("long_name")
        private String name;
        @SerializedName("color")
        private String color;
    }
}
