package com.szczep4niak.sukl.utility;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResponseParser {
    public static String getPopisChyby(String body) {
        Gson g = new Gson();
        JsonObject json = g.fromJson(body, JsonObject.class);
        return json.get("popisChyby").getAsString();
    }
}
