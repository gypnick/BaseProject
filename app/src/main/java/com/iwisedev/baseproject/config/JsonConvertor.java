package com.iwisedev.baseproject.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

/**
 * Created by Ledev2 on 2017-10-10.
 */

public class JsonConvertor {

    private static Gson gson = null;

    private JsonConvertor() {
    }

    public static Gson getInstance() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .registerTypeAdapter(Date.class, new DateTypeAdapter())
                    .create();
        }
        return gson;
    }
}
