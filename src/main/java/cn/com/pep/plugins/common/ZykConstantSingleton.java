package cn.com.pep.plugins.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 渠道json 单例
 */
public class ZykConstantSingleton {
    volatile private static ZykConstantSingleton instance = null;
    private Map zykConstantMap = new HashMap<>();


    private ZykConstantSingleton() {
        try {
            InputStream fileInput = ZykConstantSingleton.class.getClassLoader().getResourceAsStream(Constants.zyk_constant_path);
            ObjectMapper mapper = new ObjectMapper();
            zykConstantMap = mapper.readValue(fileInput, LinkedHashMap.class);
            this.setZykConstantMap(zykConstantMap);
        } catch (Exception e) {
            e.printStackTrace();
            instance = null;
        }

    }

    public static ZykConstantSingleton getInstance() {
        if (instance == null) {
            synchronized (ZykConstantSingleton.class) {
                if (instance == null) {
                    instance = new ZykConstantSingleton();
                }
            }
        }
        return instance;
    }

    public static void getRefresh() {
        instance = new ZykConstantSingleton();
    }

    public Map getZykConstantMap() {
        return zykConstantMap;
    }

    public void setZykConstantMap(Map zykConstantMap) {
        this.zykConstantMap = zykConstantMap;
    }
}
