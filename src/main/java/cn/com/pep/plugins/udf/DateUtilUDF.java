package cn.com.pep.plugins.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述:
 * 时间转换函数
 *
 * @author zhangfz
 * @create 2019-08-12 10:36
 */
public class DateUtilUDF {

    public static String evaluate(String index,String requestTime, String startTime) {
        String result = requestTime;
        if ("1".equals(index)) {
            requestTime = requestTime.substring(1, requestTime.length() - 1).replace(".", "");
            Date requestDate = new Date(Long.valueOf(requestTime));
            Date startDate = new Date(Long.valueOf(startTime));
            SimpleDateFormat formatYmd = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatHms = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String ymd = formatYmd.format(requestDate);
            String hms = formatHms.format(startDate);
            String dateStr = ymd + " " + hms;
            try {
                Date newDate = format.parse(dateStr);
                result = String.valueOf(newDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        for(int i = 0;i < 1000000; i++) {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(Long.valueOf(evaluate("1","[1565578497.000]", "1564624497000")));
            //System.out.println(format.format(date));
        }
        System.out.println(System.currentTimeMillis() - start);

    }
}
