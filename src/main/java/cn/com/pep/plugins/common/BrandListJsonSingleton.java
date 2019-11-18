package cn.com.pep.plugins.common;



import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @author:QZ
 * @date:2019/11/7 10:22
 */
public class BrandListJsonSingleton {

    private static volatile BrandListJsonSingleton instance = null;
    private Map<String,List<String>> brandMap = new LinkedHashMap();

    private BrandListJsonSingleton() throws IOException {
        InputStream resource = BrandListJsonSingleton.class.getClassLoader().getResourceAsStream(Constants.brand_list_mapping);
        ObjectMapper mapper = new ObjectMapper();
        LinkedHashMap<String,List<String>> innerMap = mapper.readValue(resource, LinkedHashMap.class);
        setBrandMap(innerMap);
    }

    public static BrandListJsonSingleton getInstance() throws IOException {
        if(instance==null){
            synchronized (BrandListJsonSingleton.class){
                if(instance == null){
                    instance = new BrandListJsonSingleton();
                }
            }
        }
        return instance;
    }

    public void setBrandMap(Map<String, List<String>> brandMap) {
        this.brandMap = brandMap;
    }

    public Map<String, List<String>> getBrandMap() {
        return brandMap;
    }

    public static void reFreshInstance() throws IOException {
        instance = new BrandListJsonSingleton();
    }

}
