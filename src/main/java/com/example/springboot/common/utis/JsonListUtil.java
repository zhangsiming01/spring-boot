package com.example.springboot.common.utis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Author: zhagnsiming
 * @CreateDate: 2018-11-28 9:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2018-11-28 9:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * json转换实体
 */
public class JsonListUtil {
        /**
         * List<T> 转 json 保存到数据库
         */
        public static <T> String listToJson(List<T> ts) {
            return  JSON.toJSONString(ts);
        }

        /**
         * json 转 List<T>
         */
        public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
            @SuppressWarnings("unchecked")
            List<T> ts = JSONArray.parseArray(jsonString, clazz);
            return ts;
        }
    /**

     * 将java对象转换成json字符串

     * @param javaObj

     * @return

     */

    public static String jsonTransformationObject(Object javaObj) {
        Object json = JSONObject.toJSON(javaObj);
        return json.toString();
    }
    /***
     * 反序列化数据
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parse(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    private JsonListUtil() {
    }
}
