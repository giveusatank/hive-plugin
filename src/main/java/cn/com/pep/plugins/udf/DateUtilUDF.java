package cn.com.pep.plugins.udf;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述:
 * 时间转换函数
 * *  create function dws.dateUtilUDF as 'cn.com.pep.plugins.udf.DateUtilUDF';
 * @author zhangfz
 * @create 2019-08-12 10:36
 */
public class DateUtilUDF extends UDF{

    public static String evaluate(String type, String requestTime) {
        String result = requestTime;
        if (requestTime != null && requestTime.length() > 0) {
            if (requestTime.length() == 10) {
                requestTime = requestTime + "000";
            }
            if ("week".equals(type)) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date(Long.valueOf(requestTime)));
                if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    cal.add(Calendar.DATE, -1);
                }
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd");
                String start = sdf1.format(cal.getTime());
                Calendar calEnd = cal;
                calEnd.add(Calendar.DAY_OF_YEAR, 6);
                String end = sdf1.format(calEnd.getTime());
                result = start + "-" + end;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(evaluate("week", "1573353064000"));
    }
}
