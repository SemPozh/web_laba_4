package com.itmo.backend.utils;

import com.google.gson.Gson;
import com.itmo.backend.model.entity.Shot;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

public class JSONUtil {
    private static final Gson gson = new Gson();
    public static String convertShotListToJSON(List<Shot> shots){
        return gson.toJson(shots);
    }

    public static String generateResponseMessage(String message){
        return String.format("{\"message\": \"%s\"}", message);
    }

    public static String generateShotResultJSON(Shot shot){
        if (shot.isByAreaClick()){
            return String.format("{\"x\": %.5f, \"y\": %.5f,\"r\": %d, \"byAreaClick\": true, \"result\": %b}", shot.getX(), shot.getY(), shot.getR(), shot.isResult());

        }
        return String.format("{\"x\": %d, \"y\": %.5f, \"r\": %d, \"byAreaClick\": false, \"result\": %b}", (int) shot.getX(), shot.getY(), shot.getR(), shot.isResult());
    }
}
