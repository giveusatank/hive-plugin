package cn.com.pep.plugins.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 教材id转换文件单例
 */
public class TbIdConvertJsonSingleton {
    volatile private static TbIdConvertJsonSingleton instance = null;
    private Map tbIdMap = new HashMap<>();


    private TbIdConvertJsonSingleton() {
        try {
            InputStream fileInput = TbIdConvertJsonSingleton.class.getClassLoader().getResourceAsStream(Constants.diandu_book_id_mapping);
            ObjectMapper mapper = new ObjectMapper();
            tbIdMap = mapper.readValue(fileInput, LinkedHashMap.class);
            this.setTbIdMap(tbIdMap);
        } catch (Exception e) {
            e.printStackTrace();
            instance = null;
        }

    }

    public static TbIdConvertJsonSingleton getInstance() {
        if (instance == null) {
            synchronized (TbIdConvertJsonSingleton.class) {
                if (instance == null) {
                    instance = new TbIdConvertJsonSingleton();
                }
            }
        }
        return instance;
    }

    public static void getRefresh() {
        instance = new TbIdConvertJsonSingleton();
    }

    public Map getTbIdMap() {
        return tbIdMap;
    }

    public void setTbIdMap(Map tbIdMap) {
        this.tbIdMap = tbIdMap;
    }
}
