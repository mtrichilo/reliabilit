package com.reliabilit.reliabilit.model;

import com.google.gson.annotations.SerializedName;

public class Station {
    @SerializedName("id")
    private String id;
    @SerializedName("attributes")
    private Attributes attributes;

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.attributes.name;
    }

    private class Attributes {
        @SerializedName("name")
        private String name;
    }
}
