package cn.com.pep.plugins.udf;


import cn.com.pep.plugins.common.EduCodeRule;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * 描述:
 * 教育编码规则解析方案
 *  rkxd 学段
 *  zxxkc 学科
 *  publisher 出版社
 *  nj 年级
 *  fascicule 册别
 *  year 年份
 *  edition 小版本
 *  bookFactor 其他因子，如：章节
 *
 *
 *  create function dws.getEduCode as 'cn.com.pep.plugins.udf.EduCodeRuleUDF';
 * @author zhangfz
 * @create 2019-05-07 16:08
 */
public class EduCodeRuleUDF extends UDF {


    public static String evaluate(String eduCode, String factorName) {
        String result = factorName;
        try {
            EduCodeRule eduCodeRule = new EduCodeRule(eduCode);
            switch (factorName) {
                case "rkxd":
                    result = eduCodeRule.getRkxdFactor().getCode();
                    break;
                case "zxxkc":
                    result = eduCodeRule.getZxxkcFactor().getCode();
                    break;
                case "publisher":
                    result = eduCodeRule.getPublisherFactor().getCode();
                    break;
                case "nj":
                    result = eduCodeRule.getNjFactor().getCode();
                    break;
                case "fascicule":
                    result = eduCodeRule.getFasciculeFactor().getCode();
                    break;
                case "year":
                    result = eduCodeRule.getYearFactor().getCode();
                    break;
                case "edition":
                    result = eduCodeRule.getEditionFactor().getCode();
                    break;
                case "chapter":
                    result = eduCodeRule.getBookFactor().getCode();
                    break;
                case "chapterCascade":
                    result = eduCodeRule.getChapterCascadeFactor().getCode();
                    break;
                case "educode":
                    result = eduCodeRule.getEduCodeFactor().getCode();
                    break;
                default:
                    result = eduCode;
                    break;
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(evaluate("1517448661897", "zxxkc"));
        System.out.println(evaluate("1517448661897", "nj"));
        System.out.println(evaluate("1517448661897", "fascicule"));
        System.out.println(evaluate("1517448661897", "rkxd"));

    }
}
