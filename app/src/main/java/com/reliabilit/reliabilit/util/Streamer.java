package com.reliabilit.reliabilit.util;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Streamer {

    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T, U> void forEach(List<T> list, BiFunction<U, T, U> function) {
        U previous = null;
        for (int i = 0; i < list.size(); i++) {
            previous = function.apply(previous, list.get(i));
        }
    }

    public static <T> boolean intersect(Collection<T> first, Collection<T> second) {
        for (T itemFirst : first) {
            if (second.contains(itemFirst)) {
                return true;
            }
        }
        return false;
    }
}
