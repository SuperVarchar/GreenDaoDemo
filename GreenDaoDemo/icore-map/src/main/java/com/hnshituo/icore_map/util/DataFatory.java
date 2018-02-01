package com.hnshituo.icore_map.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataFatory {

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz){
        Type type = new TypeToken<ArrayList<JsonObject>>()
        {}.getType();

        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
        ArrayList<T> arrayList = new ArrayList<>();

        if(jsonObjects != null){
            for (JsonObject jsonObject :jsonObjects) {
                arrayList.add(new Gson().fromJson(jsonObject, clazz));
            }
        }
        return arrayList;
    }
}