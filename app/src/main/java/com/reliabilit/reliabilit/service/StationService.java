package com.reliabilit.reliabilit.service;

import com.reliabilit.reliabilit.model.Station;

import java.io.IOException;
import java.util.List;

public interface StationService {
    /**
     * Returns a list of all station names.
     * @return All station name Strings.
     * @throws IOException If HTTP request fails.
     */
    List<String> getStationNames() throws IOException;
}
