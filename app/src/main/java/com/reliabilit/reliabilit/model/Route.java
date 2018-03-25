package com.reliabilit.reliabilit.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Route implements Model {
    @SerializedName("id")
    private String id;
    @SerializedName("attributes")
    private Attributes attributes;

    public Type getTypeToken() {
        return new TypeToken<Data<Route>>(){}.getType();
    }

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
