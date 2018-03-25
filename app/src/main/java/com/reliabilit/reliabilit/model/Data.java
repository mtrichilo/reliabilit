package com.reliabilit.reliabilit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Data<T extends Model> {
    @SerializedName("data")
    private T[] data;

    public List<T> getData() {
        if (this.data == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(this.data);
    }

    public <R> List<R> map(Function<T, R> function) {
        return this.getData().stream().map(function).collect(Collectors.toList());
    }
}
