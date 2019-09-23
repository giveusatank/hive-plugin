package cn.com.pep.plugins.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 教材id转换文件单例
 */
public class ConvertJsonSingleton {
    volatile private static ConvertJsonSingleton instance = null;
    private Map converterMap = new HashMap<>();


    private ConvertJsonSingleton(String resourcePath) {
        try {
            InputStream fileInput = ConvertJsonSingleton.class.getClassLoader().getResourceAsStream(resourcePath);
            ObjectMapper mapper = new ObjectMapper();
            converterMap = mapper.readValue(fileInput, LinkedHashMap.class);
            this.setConverterMap(converterMap);
        } catch (Exception e) {
            e.printStackTrace();
            instance = null;
        }
    }

    private ConvertJsonSingleton(){

    }

    public static ConvertJsonSingleton getInstance(String resourcePath) {
        if (instance == null) {
            synchronized (ConvertJsonSingleton.class) {
                if (instance == null) {
                    instance = new ConvertJsonSingleton(resourcePath);
                }
            }
        }
        return instance;
    }

    public static void getRefresh() {
        instance = new ConvertJsonSingleton();
    }

    public Map getConverterMap() {
        return converterMap;
    }

    public void setConverterMap(Map tbIdMap) {
        this.converterMap = tbIdMap;
    }
}
