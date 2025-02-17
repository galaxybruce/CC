package com.billy.cc.core.component.remote;

import android.content.Context;

import com.billy.cc.core.component.IParamJsonConverter;
import com.google.gson.Gson;

import androidx.fragment.app.Fragment;

/**
 * 用Gson来进行跨app调用时的json转换
 * @author billy.qi
 * @since 18/5/28 19:48
 */
public class GsonParamConverter implements IParamJsonConverter {
    Gson gson = new Gson();

    @Override
    public <T> T json2Object(String input, Class<T> clazz) {
        return gson.fromJson(input, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        if(instance instanceof Fragment || instance instanceof Context) {
            return instance.toString();
        }
        return gson.toJson(instance);
    }
}
