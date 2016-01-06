package com.sainsbury.sainsburyhtmlscraper.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * Custom serializer to format double values to print two decimal values while
 * building json string.
 *
 * @author Dinesh Mallemala
 */
public class CustomDoubleSerializer implements JsonSerializer<Double> {

    @Override
    public JsonElement serialize(Double value, Type typeOfSrc, JsonSerializationContext context) {

        return new JsonPrimitive((new BigDecimal(value)).setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
