package com.sainsbury.sainsburyhtmlscraper.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON Utility class
 *
 * @author Dinesh Mallemala
 */
public final class JSONUtils {

    /**
     * Converts the given object to json string.
     *
     * @param obj Object
     * @return java.lang.String
     */
    public static String getJsonString(Object obj) {
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Double.class, new CustomDoubleSerializer());
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.disableHtmlEscaping();

        Gson gson = gsonBuilder.create();
        return gson.toJson(obj);
    }
}
