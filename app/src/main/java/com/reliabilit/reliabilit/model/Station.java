package com.reliabilit.reliabilit.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class Station implements Model {
    @SerializedName("id")
    private String id;
    @SerializedName("attributes")
    private Attributes attributes;

    public Type getTypeToken() {
        return new TypeToken<Data<Station>>(){}.getType();
    }

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
