package com.reliabilit.reliabilit.json;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RouteJson implements Json {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("attributes")
    private Attributes attributes;

    public Type getTypeToken() {
        return new TypeToken<Data<RouteJson>>(){}.getType();
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        if (this.name != null) {
            return this.name;
        }
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
