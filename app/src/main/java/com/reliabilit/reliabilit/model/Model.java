package com.reliabilit.reliabilit.model;

import java.lang.reflect.Type;

public interface Model {
    /**
     * Returns the Type of a {@link com.google.gson.reflect.TypeToken}
     * using this class as part of a generic parameter.
     * @return This model's Type.
     */
    Type getTypeToken();
}
