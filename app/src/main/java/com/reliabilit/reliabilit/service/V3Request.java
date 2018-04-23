package com.reliabilit.reliabilit.service;

class V3Request extends Request {
    private static final String V3_BASE = "https://api-v3.mbta.com";
    private static final String V3_KEY = "14e087e22b2049df97d155d77605fccb";

    V3Request() {
        super(V3_BASE, V3_KEY);
    }
}
