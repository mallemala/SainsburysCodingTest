/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sainsbury.sainsburyhtmlscraper.gsonserializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 *
 * @author Dinesh Mallemala
 */
public class CustomDoubleSerializer implements JsonSerializer<Double> {

    @Override
    public JsonElement serialize(Double value, Type typeOfSrc, JsonSerializationContext context) {

        return new JsonPrimitive((new BigDecimal(value)).setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
