package cn.com.pep.plugins.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description:
 * @author:QZ
 * @date:2019/9/19 10:56
 */
public class OrderJsonSingeton {

    private static volatile OrderJsonSingeton orderJsonSingeton = null;
    private Map orderMap = null;

    private OrderJsonSingeton(String resourcePath) throws IOException {
        InputStream resourceAsStream = OrderJsonSingeton.class.getClassLoader().getResourceAsStream(resourcePath);
        ObjectMapper objectMapper = new ObjectMapper();
        this.orderMap = objectMapper.readValue(resourceAsStream, LinkedHashMap.class);
    }

    public static OrderJsonSingeton getInstance(String resourcePath) throws IOException {

        if(orderJsonSingeton == null){
            synchronized (OrderJsonSingeton.class) {
                if(orderJsonSingeton == null){
                    orderJsonSingeton = new OrderJsonSingeton(resourcePath);
                }
            }
        }
        return orderJsonSingeton;
    }

    public static OrderJsonSingeton getOrderJsonSingeton() {
        return orderJsonSingeton;
    }

    public static void setOrderJsonSingeton(OrderJsonSingeton orderJsonSingeton) {
        OrderJsonSingeton.orderJsonSingeton = orderJsonSingeton;
    }

    public Map getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map orderMap) {
        this.orderMap = orderMap;
    }
}
