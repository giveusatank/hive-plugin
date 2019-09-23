package cn.com.pep.plugins.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.*;

/**
 * 描述:
 * 耗时处理
 *
 * @author zhangfz
 * @create 2018-08-02 15:49
 */
public class TimeConsume extends UDF {
    /*
    {
    "1000000000100": "dd100001",
    "1000000000200": "dd100001",
    "1000000000300": "dd100002",
    "1000000000400": "dd100002",
    "1000000000500": "dd100002",
    "1000000000600": "dd100001",
    "1000000000700": "dd100001",
    "1000000000800": "dd100001",
    "1000000000900": "dd100002",
    "1000000001000": "dd100001",
    "1000000001100": "jx100001",
    "1000000001200": "jx100002"
}
     */
    private static final long HALF_DAY_MILLISECOND = 43200000L;
    private static final Long ONE_DAY_MILLISECOND = HALF_DAY_MILLISECOND * 2;
    // timeMap : start_time action_title
    // startAction:jx10001,dd10001
    // endAction:jx10002,dd10002
    public static long evaluate(Map<String, String> timeMap, String startAction, String endAction, int returnType) {
        try {
            if (null != timeMap && timeMap.size() > 0) {
                timeMap = sortMapByKey(timeMap);
                return consumeTimeHandler(timeMap, startAction, endAction, returnType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        String timeMapStr = "{\"1000000000100\":\"dd100001\",\"1000000000200\":\"dd100001\",\"1000000000300\":\"dd100001\"," +
                "\"1000000000400\":\"dd100001\",\"1000000000900\":\"dd100002\",\"1000000000600\":\"dd100001\",\"1000000000700\":\"dd100001\"," +
                "\"1000000000800\":\"dd100001\",\"1000000000900\":\"dd100002\",\"1000000001000\":\"dd100001\",\"1000000001100\":\"jx100001\"," +
                "\"1000000001200\":\"jx100002\"}";
        String startAction = "dd100001,jx100001";
        String endAction = "dd100002,jx100002";
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> timeMap = mapper.readValue(timeMapStr, TreeMap.class);
            /*
                   100  dd10001
                   200  dd10001
                   300  dd10001
                   400  dd10001

                   600  dd10001
                   700  dd10001
                   800  dd10001
                   900  dd10002
                   1000 dd10001
                   1100 jx10001
                   1200 jx10002

             */
            System.out.println(evaluate(timeMap, startAction, endAction, 0));
        } catch (Exception e) {
            e.printStackTrace();
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
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    public static Long consumeTimeHandler(Map<String, String> timeMap, String startAction, String endAction, int returnType) {
        boolean startFlag = true;
        int count = 0;
        long start_action_time = 0;
        long consumeTime = 0L;
        long avgConsume = 0L;
        if (timeMap != null && timeMap.size() > 0) {
            //遍历timeMap<start_time,action_title> 判断每个action_title是否是start_action
            for (Map.Entry<String, String> entry : timeMap.entrySet()) {
                //endAction :结束动作(dd100002,jx200002)   action_title
                //按照时间排序后，如果第一个action_title是一个结束动作，那么就continue，判断下一个entry
                if (startFlag && judgeTitleInStartOrEnd(endAction, entry.getValue())) {//find first start action
                    continue;
                } else {

                    if (start_action_time > 0) { // find end action
                        if (judgeTitleInStartOrEnd(startAction,entry.getValue())) {

                            start_action_time = Long.valueOf(entry.getKey());// use the start action up to date
                        } else if (judgeTitleInStartOrEnd(endAction, entry.getValue())) {
                            if (entry.getKey() != null && entry.getKey().length() == 13) {
                                long end_action_time = Long.valueOf(entry.getKey());
                                long cTime = end_action_time - start_action_time;
                                if (cTime <= ONE_DAY_MILLISECOND) {//每次学习时间不超过1天
                                    consumeTime = consumeTime + cTime;
                                    start_action_time = 0L;
                                    count++;
                                }
                            }
                        }
                    } else {
                        //找到了第一个start动作
                        if (judgeTitleInStartOrEnd(startAction,entry.getValue())) {//find start action
                            if (entry.getKey() != null && entry.getKey().length() == 13) {
                                start_action_time = Long.valueOf(entry.getKey());
                                startFlag = false;
                            }
                        }
                    }
                }
            }
        }
        if (count > 0) {
            avgConsume = consumeTime / count;
        }
        if (returnType == 1) {
            return avgConsume;
        } else {
            return consumeTime;
        }
    }

    public static boolean judgeTitleInStartOrEnd(String actionTitles, String action) {
        try {
            if (actionTitles.indexOf(action) > -1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

class MapKeyComparator implements Comparator<String> {

    @Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}
