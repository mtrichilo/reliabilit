package com.reliabilit.reliabilit.model;

import android.graphics.Color;

import java.util.Arrays;
import java.util.List;

public enum Line {
    GREEN_B("Green Line (B)"),
    GREEN_C("Green Line (C)"),
    GREEN_D("Green Line (D)"),
    GREEN_E("Green Line (E)"),
    RED("Red Line"),
    ORANGE("Orange Line"),
    BLUE("Blue Line");

    private String text;

    private Line(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static List<Line> getAll() {
        return Arrays.asList(GREEN_B, GREEN_C, GREEN_D, GREEN_E, RED, ORANGE, BLUE);
    }
}
