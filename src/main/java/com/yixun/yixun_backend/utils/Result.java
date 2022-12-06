package com.yixun.yixun_backend.utils;

import javax.servlet.ServletRegistration.Dynamic;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;


public class Result {
    public int errorCode;
    public boolean status;

    public Map<String, Object> data = new HashMap<>();

    public Result()
    {
        errorCode = 300;
        status = false;
    }

    public String ReturnJson(){
        String jsonStr = JSON.toJSONString(this);
        return jsonStr;
    }

    public static Result error() {
        return new Result();
    }

    };