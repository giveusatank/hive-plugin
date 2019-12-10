package cn.com.pep.plugins.udf;

import net.ipip.ipdb.City;
import net.ipip.ipdb.CityInfo;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.io.InputStream;

/**
 * 描述:
 * ip解析城市
 *
 * @author zhangfz
 * @create 2019-07-01 10:01
 */
public class IP2Region extends UDF {
    private static City cityDb;
    private static InputStream inputStream;
    static{
        try {
            inputStream = IP2Region.class.getClassLoader().getResourceAsStream("ipipfree.ipdb");
            cityDb = new City(inputStream);

        } catch (Exception e) {
        }
    }

    public static String evaluate(String ip, int index) {
        String result = "";
        try {
            CityInfo info = cityDb.findInfo(ip.trim(), "CN");
            String country_name = info.getCountryName();
            if ("局域网".equals(country_name)) {
                country_name = "中国";
            }
            String region_name = info.getRegionName();
            if ("局域网".equals(region_name)) {
                region_name = "北京";
            }
            String city_name = info.getCityName();
            if ("局域网".equals(city_name)) {
                city_name = "北京";
            }
            switch (index) {
                case 0 :
                    result = country_name;
                    break;
                case 1:
                    result = region_name;
                    if (region_name != null && region_name.equals("中国")) {
                        result = "";
                    }
                    break;
                case 2:
                    result = city_name;
                    if (city_name != null && city_name.equals("中国")) {
                        result = "";
                    }
                    break;
                default:
                    result = country_name;
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static String evaluate(String ip) {
        try {
            CityInfo info = cityDb.findInfo(ip.trim(), "CN");
            String country_name = info.getCountryName();
            String region_name = info.getRegionName();
            if (region_name != null && region_name.equals("中国")) {
                region_name = "";
            }
            String city_name = info.getCityName();
            if (city_name != null && city_name.equals("中国")) {
                city_name = "";
            }
            if ("局域网".equals(country_name)) {
                country_name = "中国";
            }
            if ("局域网".equals(region_name)) {
                region_name = "北京";
            }
            if ("局域网".equals(city_name)) {
                city_name = "北京";
            }
            return country_name + "~" + region_name + "~" + city_name;
        } catch (Exception e) {
            return "";
        }
    }

    public static void main(String[] args) {
        System.out.println(IP2Region.evaluate("223.104.49.120"));

    }





}
