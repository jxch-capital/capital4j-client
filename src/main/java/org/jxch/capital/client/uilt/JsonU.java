package org.jxch.capital.client.uilt;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

public class JsonU {

    public static String toPrettyFillJsonString(Object obj) {
        return JSON.toJSONString(obj, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteNulls);
    }

}
