package cn.com.pep.plugins.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Description:
 * @author:QZ
 * @date:2019/12/3 15:17
 */
public class AndroidListJsonSingleton {

    public static volatile AndroidListJsonSingleton instance = null;
    private LinkedHashMap<String,List<String>> map = null;

    private AndroidListJsonSingleton()  {
        try{
            InputStream resourceAsStream = AndroidListJsonSingleton.class.getClassLoader().getResourceAsStream(Constants.android_net_list);
            ObjectMapper objectMapper = new ObjectMapper();
            map = objectMapper.readValue(resourceAsStream, LinkedHashMap.class);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static AndroidListJsonSingleton getInstance(){
        if(instance == null){
            synchronized (AndroidListJsonSingleton.class){
                //进行DCL
                if(instance==null){
                    instance = new AndroidListJsonSingleton();
                }
            }
        }
        return instance;
    }

    public static void refresh(){
        instance = new AndroidListJsonSingleton();
    }

    public LinkedHashMap getMap(){
        return map;
    }
}
