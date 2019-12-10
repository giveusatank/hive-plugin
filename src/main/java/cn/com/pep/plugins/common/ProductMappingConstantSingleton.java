package cn.com.pep.plugins.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 渠道json 单例
 */
public class ProductMappingConstantSingleton {
    volatile private static ProductMappingConstantSingleton instance = null;
    private Map productConstantMap = new HashMap<>();


    private ProductMappingConstantSingleton() {
        try {
            InputStream fileInput = ProductMappingConstantSingleton.class.getClassLoader().getResourceAsStream(Constants.product_name_mapping);
            ObjectMapper mapper = new ObjectMapper();
            productConstantMap = mapper.readValue(fileInput, LinkedHashMap.class);
            this.setProductConstantMap(productConstantMap);
        } catch (Exception e) {
            e.printStackTrace();
            instance = null;
        }

    }

    public static ProductMappingConstantSingleton getInstance() {
        if (instance == null) {
            synchronized (ProductMappingConstantSingleton.class) {
                if (instance == null) {
                    instance = new ProductMappingConstantSingleton();
                }
            }
        }
        return instance;
    }

    public static void getRefresh() {
        instance = new ProductMappingConstantSingleton();
    }

    public Map getProductConstantMap() {
        return productConstantMap;
    }

    public void setProductConstantMap(Map productConstantMap) {
        this.productConstantMap = productConstantMap;
    }
}
