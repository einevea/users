package com.einevault.users;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by einevea on 16/08/15.
 */
public class JsonUtil {
    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }
    public static ResponseTransformer json() {
        return JsonUtil::toJson;
    }
}
