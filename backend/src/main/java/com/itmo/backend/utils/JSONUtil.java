package com.itmo.backend.utils;

import com.google.gson.Gson;
import com.itmo.backend.model.entity.Shot;

import java.util.ArrayList;
import java.util.List;

public class JSONUtil {
    private static final Gson gson = new Gson();
    public static String convertShotListToJSON(List<Shot> shots){
        return gson.toJson(shots);
    }
}
