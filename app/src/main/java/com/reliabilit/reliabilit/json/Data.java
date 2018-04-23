package com.reliabilit.reliabilit.json;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Data<T extends Json> {
    @SerializedName("data")
    private List<T> data;

    public List<T> getData() {
        if (this.data == null) {
            return new ArrayList<>();
        }
        return this.data;
    }

    public void foreach(Consumer<T> consumer) {
        this.getData().forEach(consumer);
    }

    public <R> List<R> map(Function<T, R> function) {
        return this.getData().stream().map(function).collect(Collectors.toList());
    }
}
