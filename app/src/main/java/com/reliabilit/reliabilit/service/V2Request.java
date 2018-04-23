package com.reliabilit.reliabilit.service;

class V2Request extends Request {
    private static final String V2_BASE = "https://realtime.mbta.com/developer/api/v2.1";
    private static final String V2_KEY = "-ixsD5PpoE2bxfhE5W9Vtg";

    V2Request() {
        super(V2_BASE, V2_KEY);
        this.addParameter("format", "json");
    }

    @Override
    protected void reset() {
        super.reset();
        this.addParameter("format", "json");
    }
}
