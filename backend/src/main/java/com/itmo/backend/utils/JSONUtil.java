package com.itmo.backend.utils;

import com.google.gson.Gson;
import com.itmo.backend.model.entity.Shot;

import java.util.List;

public class JSONUtil {
    private static final Gson gson = new Gson();
    public static String convertShotListToJSON(List<Shot> shots){
        class ShotListWrapper{
            private List<Shot> shots;

            public List<Shot> getShots() {
                return shots;
            }

            public ShotListWrapper(List<Shot> shots){
                this.shots = shots;
            }
        }

        ShotListWrapper shotListWrapper = new ShotListWrapper(shots);
        return gson.toJson(shotListWrapper);
    }
}
