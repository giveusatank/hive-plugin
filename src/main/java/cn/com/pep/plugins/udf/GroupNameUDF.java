package cn.com.pep.plugins.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述:
 * 时间转换函数
 * *  create function dws.GroupNameUDF as 'cn.com.pep.plugins.udf.GroupNameUDF';
 *
 * @author zhangfz
 * @create 2019-08-12 10:36
 */
public class GroupNameUDF extends UDF{

    public static String evaluate(String type, String time) {
        String result = "";
        /**
         * case when cast(avg_user_used_time as BIGINT) < 30 then '0-30秒'
         when cast(avg_user_used_time as BIGINT) <= 60 then '30秒-1分钟'
         when cast(avg_user_used_time as BIGINT) <= 300 then '1分钟-5分钟'
         when cast(avg_user_used_time as BIGINT) <= 600 then '5分钟-10分钟'
         when cast(avg_user_used_time as BIGINT) <= 1200 then '10分钟-20分钟'
         when cast(avg_user_used_time as BIGINT) <= 1800 then '20分钟-30分钟'
         when cast(avg_user_used_time as BIGINT) >= 1800 then '大于30分钟' end,
         */
        try {
            if (time != null && time.length() > 0) {
                if ("avg_user_used_time".equals(type)) {
                    Double timeDouble = Double.valueOf(time);
                    if (timeDouble < 30) {
                        result = "0-30秒";
                    }
                    else if (timeDouble <= 60) {
                        result = "0秒-1分钟";
                    }
                    else if (timeDouble <= 300) {
                        result = "1分钟-5分钟";
                    }
                    else if (timeDouble <= 600) {
                        result = "5分钟-10分钟";
                    }
                    else if (timeDouble <= 1200) {
                        result = "10分钟-20分钟";
                    }
                    else if (timeDouble <= 1800) {
                        result = "20分钟-30分钟";
                    }
                    else if (timeDouble > 1800) {
                        result = "大于30分钟";
                    }
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(evaluate("avg_user_used_time", "11"));
    }
}
