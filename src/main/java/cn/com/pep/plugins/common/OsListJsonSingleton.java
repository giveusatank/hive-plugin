package cn.com.pep.plugins.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

/**
 * @Description:
 * @author:QZ
 * @date:2019/12/10 9:17
 */
public class OsListJsonSingleton {

    public volatile static OsListJsonSingleton instance = null;
    private LinkedHashMap<String,String> osMap = null;

    private OsListJsonSingleton()  {
        try{
            InputStream is = OsListJsonSingleton.class.getClassLoader().getResourceAsStream(Constants.windows_os_list);
            ObjectMapper objectMapper = new ObjectMapper();
            osMap = objectMapper.readValue(is, LinkedHashMap.class);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static OsListJsonSingleton getInstance(){
        if(instance == null){
            synchronized (OsListJsonSingleton.class){
                if(instance == null){
                    instance = new OsListJsonSingleton();
                }
            }
        }
        return instance;
    }

    public LinkedHashMap<String, String> getOsMap(){
        return osMap;
    }

    public static void refresh(){
        instance = new OsListJsonSingleton();
    }
}
