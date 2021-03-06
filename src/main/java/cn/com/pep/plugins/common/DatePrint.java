package cn.com.pep.plugins.common;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

/**
 * 描述:
 *
 * @author zhangfz
 * @create 2019-09-16 16:25
 */
public class DatePrint {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.set(2018,06,01);
        Long stopDateStamp = new Date().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        long t = 0L;
        int mm = 6;
        while (t < stopDateStamp) {
            int m = cal.get(Calendar.MONTH);
            if (m != mm) {
                System.out.println();
                System.out.println();
                System.out.println();
                mm = m;
            }
            System.out.print(format.format(cal.getTime()) + " ");
            cal.add(Calendar.DAY_OF_YEAR,1);
            t = cal.getTimeInMillis();
        }
    }
}
