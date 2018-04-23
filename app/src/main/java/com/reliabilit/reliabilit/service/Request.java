package com.reliabilit.reliabilit.service;

import com.google.gson.Gson;
import com.reliabilit.reliabilit.json.Data;
import com.reliabilit.reliabilit.json.Json;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class Request {
    private String base;
    private String key;
    private Map<String, String> parameters;

    Request(String base, String key) {
        this.base = base;
        this.key = key;
        this.parameters = new HashMap<>();
        this.addParameter("api_key", key);
    }

    public Request addParameter(String key, Object value) {
        this.parameters.put(key, value.toString());
        return this;
    }

    private String getPath() {
        List<String> formatted = new ArrayList<>();
        this.parameters.forEach((key, value) -> formatted.add(String.format("%s=%s", key, value)));
        return String.join("&", formatted);
    }

    protected void reset() {
        this.parameters = new HashMap<>();
        this.addParameter("api_key", this.key);
    }

    public <T> T makeRequest(String root, Class<T> tClass) {
        T tObject = null;
        try {
            URL url = new URL(this.base + root + this.getPath());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/vnd.api+json");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                tObject = new Gson().fromJson(reader, tClass);
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.reset();
        return tObject;
    }

    public <T extends Json> Data<T> makeDataRequest(String root, Class<T> tClass) {
        Data<T> tObject = new Data<>();
        try {
            URL url = new URL(this.base + root + this.getPath());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/vnd.api+json");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                Type type = tClass.newInstance().getTypeToken();
                tObject = new Gson().fromJson(reader, type);
                reader.close();
            }
        } catch (IOException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        this.reset();
        return tObject;
    }
}
