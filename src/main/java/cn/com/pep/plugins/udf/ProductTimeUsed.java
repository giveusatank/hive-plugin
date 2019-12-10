package cn.com.pep.plugins.udf;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 描述:
 * 产品使用时长统计
 ** *  create function dws.productTimeUsed as 'cn.com.pep.plugins.udf.ProductTimeUsed';
 * @author zhangfz
 * @create 2019-11-25 14:06
 */
public class ProductTimeUsed  extends UDF {

    /**
     * 100000000 aaa
     * 100010000 bbb
     * 100050000 fff
     * 200000000 ddd
     * 200100000 fff
     * @param args
     */
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("100000000", "aaa");
        map.put("200100000", "aaa");
        map.put("100010000", "aaa");
        map.put("100050000", "aaa");
        map.put("200000000", "aaa");

        System.out.println(evaluate(map,300,0));

    }

    /**
     *
     * @param timeMap
     * @param interval 秒
     * @param returnType 0 用时，1平均每次用时，2 启动次数
     * @return
     */
    public static long evaluate(Map<String, String> timeMap, long interval, int returnType) {
        try {
            if (null != timeMap && timeMap.size() > 0) {
                timeMap = sortMapByKey(timeMap);
                return consumeTimeHandler(timeMap, interval, returnType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static Long consumeTimeHandler(Map<String, String> timeMap,long interval, int returnType) {
        long startTime = 0L;
        long usedTime = 0L;
        long sumUsedTime = 0L;
        long usedCount = 1L;
        long avgUsedTime = 0L;
        if (timeMap != null && timeMap.size() > 0) {
            int n = 0;
            for (Map.Entry<String, String> entry : timeMap.entrySet()) {
                if (null != entry.getKey() && entry.getKey().length() > 0) {
                    if (n == 0) {
                        startTime = Long.valueOf(entry.getKey());
                    }else if (n > 0) {
                        usedTime = Long.valueOf(entry.getKey()) - startTime;
                        if (usedTime < interval * 1000) {
                            sumUsedTime += usedTime;
                        }
                        else {
                            usedCount++;
                        }
                        startTime = Long.valueOf(entry.getKey());
                    }
                    n++;
                }
            }
        }
        if (usedCount > 0) {
            avgUsedTime = sumUsedTime / usedCount;
        }
        if (returnType == 0) {
            return sumUsedTime;
        } else if(returnType == 1){
            return avgUsedTime;
        }
        else if (returnType == 2) {
            return usedCount;
        }
        else {
            return sumUsedTime;
        }
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparatorPTU());
        sortMap.putAll(map);
        return sortMap;
    }
}

class MapKeyComparatorPTU implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}
