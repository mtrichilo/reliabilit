package com.reliabilit.reliabilit.json;

import java.lang.reflect.Type;

public interface Json {
    /**
     * Returns the Type of a {@link com.google.gson.reflect.TypeToken}
     * using this class as part of a generic parameter.
     * @return This json's Type.
     */
    Type getTypeToken();
}
