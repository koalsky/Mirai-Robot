package com.hyq.robot.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import freemarker.ext.beans.StringModel;
import freemarker.template.DefaultListAdapter;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 属性文件获取工具类(仅json)
 */
public class JsonDataUtils {

    private static SerializeConfig mapping = new SerializeConfig();

    /*
     * 根据json文件名称获取json配置文件数据
     *
     * @param fileName json文件名称前缀，如果在resource下直接写文件名，如果有路径，请在前面添加路径如："com/xxx/abc"
     */
    public static JSONObject getJsonObject(String fileName) {
        JSONObject jsonObject = new JSONObject();
        fileName += ".json";
        try {
            File jsonFile = ResourceUtils.getFile("classpath:" + fileName);
            String json = FileUtils.readFileToString(jsonFile, "utf-8");
            jsonObject = JSON.parseObject(json);
        } catch (IOException e) { }
        return jsonObject;
    }

    /*
     * 根据json文件名称获取json配置文件数据
     *
     * @param fileName json文件名称前缀，如果在resource下直接写文件名，如果有路径，请在前面添加路径如："com/xxx/abc"
     */
    public static JSONArray getJsonArray(String fileName) {
        JSONArray jsonArray = new JSONArray();
        fileName += ".json";
        try {
            File jsonFile = ResourceUtils.getFile("classpath:" + fileName);
            String json = FileUtils.readFileToString(jsonFile, "utf-8");
            jsonArray = JSON.parseArray(json);
            System.out.println();
        } catch (IOException e) { }
        return jsonArray;
    }

    /**
     * 获取json字符串
     * @param fileName
     * @return
     */
    public static String getJsonString(String fileName) {

        String json = null;
        fileName += ".json";
        try {
            File jsonFile = ResourceUtils.getFile("classpath:" + fileName);
            json = FileUtils.readFileToString(jsonFile, "utf-8");
        } catch (IOException e) { }
        return json;
    }

    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer(
                "yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * javaBean、list、map convert to json string
     */
    public static String obj2json(Object obj) {
        return JSON.toJSONString(obj, mapping);
    }

    /**
     * javaBean、list、map convert to json string
     */
    public static String obj2Json(Object obj, Boolean formatted) {
        // 在上面的代码中添加下面的这行代码，则可以将转换后的字段名称的引号去掉。
        // serializer.config(SerializerFeature.QuoteFieldNames, false);
        // SerializerFeature.UseSingleQuotes 使用单引号
        return formatted ? JSON.toJSONString(obj, mapping,
                SerializerFeature.PrettyFormat) : JSON.toJSONString(obj,
                mapping);// 格式化数据，方便阅读
    }

    /**
     * json string convert to javaBean、map
     */
    public static <T> T json2obj(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {
        return JSON.parseArray(jsonArrayStr, clazz);
    }

    /**
     * json string convert to map
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, Object> json2map(String jsonStr) {
        return json2obj(jsonStr, Map.class);
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> json2map(String jsonStr, Class<T> clazz) {
        Map<String, T> map = JSON.parseObject(jsonStr,
                new TypeReference<Map<String, T>>() {
                });
        for (Map.Entry<String, T> entry : map.entrySet()) {
            JSONObject obj = (JSONObject) entry.getValue();
            map.put(entry.getKey(), JSONObject.toJavaObject(obj, clazz));
        }
        return map;
    }

}