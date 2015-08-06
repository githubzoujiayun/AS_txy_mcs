package com.txy.gson;

import com.google.gson.Gson;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2015/8/6.
 */
public final class GsonUtils {

    private GsonUtils(){}

    public static <T> T parseJSON(String json, Class<T> clazz) {
        Gson gson = new Gson();
        T info = gson.fromJson(json, clazz);
        return info;
    }

    /**
     * Type type = new TypeToken<ArrayList<TypeInfo>>(){}.getType();
     Type所在的包：java.lang.reflect
     TypeToken所在的包：com.google.gson.reflect.TypeToken
     */
    public static <T> T parseJSONArray(String jsonArr, Type type) {
        Gson gson = new Gson();
        T infos = gson.fromJson(jsonArr, type);
        return infos;
    }
}
