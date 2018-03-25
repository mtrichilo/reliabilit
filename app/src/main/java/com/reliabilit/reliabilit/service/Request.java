package com.reliabilit.reliabilit.service;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Request {
    private static final String V3_BASE = "https://api-v3.mbta.com";
    private static final String V3_KEY = "14e087e22b2049df97d155d77605fccb";
    private static final String V2_KEY = "-ixsD5PpoE2bxfhE5W9Vtg";

    private Map<String, String> parameters;

    Request() {
        this.parameters = new HashMap<>();
        this.addParameter("api_key", V3_KEY);
    }

    public Request addParameter(String key, String value) {
        this.parameters.put(key, value);
        return this;
    }

    private String getPath() {
        List<String> formatted = new ArrayList<>();
        this.parameters.forEach((key, value) -> formatted.add(String.format("%s=%s", key, value)));
        return String.join("&", formatted);
    }

    private void reset() {
        this.parameters = new HashMap<>();
        this.addParameter("api_key", V3_KEY);
    }

    public <T> T makeRequest(String root, Class<T> tClass) throws IOException {
        URL url = new URL(V3_BASE + root + this.getPath());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/vnd.api+json");

        T tObject = null;
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            tObject = new Gson().fromJson(reader, tClass);
            reader.close();
        }

        this.reset();
        return tObject;
    }
}
