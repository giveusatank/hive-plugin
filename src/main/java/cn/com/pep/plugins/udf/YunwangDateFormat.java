package cn.com.pep.plugins.udf;

import cn.com.pep.plugins.common.CompanyJsonSingleton;
import cn.com.pep.plugins.common.Constants;
import cn.com.pep.plugins.common.ConvertJsonSingleton;
import cn.com.pep.plugins.common.OrderJsonSingeton;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述:
 * 云望大数据平台数据处理udf
 * tbid:新旧教材id转换
 * <p>
 * create function dws.yunwangDateFormat as 'cn.com.pep.plugins.udf.YunwangDateFormat';
 *
 * @author zhangfz
 * @create 2019-05-07 9:44
 */
public class YunwangDateFormat extends UDF {

    public static String evaluate(String dataType, String key) throws IOException, IOException {
        if ("tbid".equals(dataType)) {
            Map<String, String> tbIdMap = ConvertJsonSingleton.getInstance(Constants.diandu_book_id_mapping).getConverterMap();
            if (key == null) {
                return key;
            }
            String[] strarray = key.split(",");
            //key中不包含","分割
            if (strarray.length == 1) {
                if (tbIdMap.get(strarray[0]) == null || tbIdMap.get(strarray[0]).length() < 1) {
                    return key;
                }
                return tbIdMap.get(strarray[0]);
            }
            //key中有一个","，分四种情况，老教材id+页码， 新教材id+页码,动作+新教材id，动作+旧教材id
            else if (strarray.length == 2) {
                Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m = p.matcher(strarray[0]);
                if (m.find()) { //只有动作才包含中文，说明第一个字段是动作，
                    if (tbIdMap.get(strarray[1]) == null || tbIdMap.get(strarray[1]).length() < 1) {
                        return key;
                    }
                    return tbIdMap.get(strarray[1]);
                }
                else { //说明是教材id+页码
                    StringBuffer sb_key = new StringBuffer();
                    sb_key.append(tbIdMap.get(strarray[0]));
                    sb_key.append(",");
                    sb_key.append(strarray[1]);
                    return sb_key.toString();
                }
            }
            else if (strarray.length == 3) {//动作+老教材id+页码
                StringBuffer sb_key = new StringBuffer();
                sb_key.append(tbIdMap.get(strarray[1]) == null?strarray[1]:tbIdMap.get(strarray[1]));
                sb_key.append(",");
                sb_key.append(strarray[2]);
                return sb_key.toString();
            }
            else {
                return key;
            }
        }else if("order".equals(dataType)){
            OrderJsonSingeton instance = OrderJsonSingeton.getInstance(Constants.order_product_company_mapping);
            HashMap<String,String> hashMap = (HashMap<String, String>) instance.getOrderMap();
            if(hashMap.containsKey(key)) {
                return hashMap.get(key);
            }else {
                return key;
            }
        } else if("company".equals(dataType)){
            CompanyJsonSingleton instance = CompanyJsonSingleton.getInstance();
            HashMap<String, String> hashMap = (HashMap<String, String>) instance.getCompanyMap();
            if(hashMap.containsKey(key)) {
                return key;
            }else {
                return "";

            }
        } else if("company2name".equals(dataType)){
            CompanyJsonSingleton instance = CompanyJsonSingleton.getInstance();
            HashMap<String, String> hashMap = (HashMap<String, String>) instance.getCompanyMap();
            if(hashMap.containsKey(key)) {
                return hashMap.get(key);
            }else {
                return "";

            }
        }
        return key;
    }

    //    public static void main(String[] args)
    //    {
    //        System.out.println(evaluate("tbid", "教材打开,tape3b_002003"));
    //    }
    public static void main(String[] args) throws IOException {
        System.out.println(evaluate("order", "49"));
    }
}