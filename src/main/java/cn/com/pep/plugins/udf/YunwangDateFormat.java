package cn.com.pep.plugins.udf;

import cn.com.pep.plugins.common.*;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.io.IOException;
import java.util.*;
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
public class YunwangDateFormat extends UDF{

    public static String evaluate(String dataType, String key) throws IOException {
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
        } else if("zyk_ysj".equals(dataType)){
            ZykConstantSingleton instance = ZykConstantSingleton.getInstance();
            HashMap<String, String> hashMap = (HashMap<String, String>) instance.getZykConstantMap();
            if(hashMap.containsKey(key)) {
                return hashMap.get(key);
            }else {
                return "";

            }
        } else if("product".equals(dataType)){
            ProductMappingConstantSingleton instance = ProductMappingConstantSingleton.getInstance();
            HashMap<String, String> hashMap = (HashMap<String, String>) instance.getProductConstantMap();
            if(hashMap.containsKey(key)) {
                return key;
            }else {
                return "";

            }
        } else if("product2name".equals(dataType)) {
            ProductMappingConstantSingleton instance = ProductMappingConstantSingleton.getInstance();
            HashMap<String, String> hashMap = (HashMap<String, String>) instance.getProductConstantMap();
            if (hashMap.containsKey(key)) {
                return hashMap.get(key);
            }
            else {
                return "";
            }
        } else if("brand".equals(dataType)){
            if(key!=null && key!=""){
                BrandListJsonSingleton instance = BrandListJsonSingleton.getInstance();
                Map<String, List<String>> brandMap = instance.getBrandMap();
                Set<Map.Entry<String, List<String>>> entries = brandMap.entrySet();
                String returnBrand = null;
                outter:for(Map.Entry<String, List<String>> entry:entries){
                    String brandName = entry.getKey();
                    List<String> lists = entry.getValue();
                    //遍历list，比较传入key是否包含list元素
                    inner:for(String item:lists){
                        String upperKey = key.toUpperCase();
                        String upperItem = item.toUpperCase();
                        if(upperKey.contains(upperItem)){
                            returnBrand = brandName;
                            break outter;
                        }
                    }
                }
                if(returnBrand==null){
                    returnBrand="其他";
                }
                return returnBrand;
            }else {
                return "";
            }
        }else if("connect".equals(dataType)){
            String res = null;
            if(!"".equals(key) && key != null){
                AndroidListJsonSingleton instance = AndroidListJsonSingleton.getInstance();
                LinkedHashMap<String,List<String>> map = instance.getMap();
                outter:for(Map.Entry<String, List<String>> item : map.entrySet()){
                    String key_ = item.getKey();
                    inner:for(String value:item.getValue()){
                        if(key.equals(String.valueOf(value))){
                            //找到跳出外层循环
                            res = key_;
                            break outter;
                        }
                    }
                }
            }else {
                return "";
            }
            return res;
        }else if("os".equals(dataType)){
            LinkedHashMap<String, String> osMap = OsListJsonSingleton.getInstance().getOsMap();
            if(!"".equals(key) && key!=null){
                for(Map.Entry<String, String> entry:osMap.entrySet()){
                    if(key.contains(entry.getKey())){
                        return entry.getValue();
                    }
                }
            }
        }
        return key;
    }

    //    public static void main(String[] args)
    //    {
    //        System.out.println(evaluate("tbid", "教材打开,tape3b_002003"));
    //    }
    public static void main(String[] args) throws IOException {
        //System.out.println(evaluate("brand", "iphone"));
        //System.out.println(evaluate("connect","15"));
        //System.out.println("~~~~~~~~~~~~~~~~~~~``");
        OsListJsonSingleton instance = OsListJsonSingleton.getInstance();
        System.out.println(evaluate("os", "Windows NTwww"));
    }
}
