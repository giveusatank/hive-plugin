package cn.com.pep.plugins.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 渠道json 单例
 */
public class CompanyJsonSingleton {
    volatile private static CompanyJsonSingleton instance = null;
    private Map companyMap = new HashMap<>();


    private CompanyJsonSingleton() {
        try {
            InputStream fileInput = CompanyJsonSingleton.class.getClassLoader().getResourceAsStream(Constants.company_product_mapping);
            ObjectMapper mapper = new ObjectMapper();
            companyMap = mapper.readValue(fileInput, LinkedHashMap.class);
            this.setCompanyMap(companyMap);
        } catch (Exception e) {
            e.printStackTrace();
            instance = null;
        }

    }

    public static CompanyJsonSingleton getInstance() {
        if (instance == null) {
            synchronized (CompanyJsonSingleton.class) {
                if (instance == null) {
                    instance = new CompanyJsonSingleton();
                }
            }
        }
        return instance;
    }

    public static void getRefresh() {
        instance = new CompanyJsonSingleton();
    }

    public Map getCompanyMap() {
        return companyMap;
    }

    public void setCompanyMap(Map companyMap) {
        this.companyMap = companyMap;
    }
}
