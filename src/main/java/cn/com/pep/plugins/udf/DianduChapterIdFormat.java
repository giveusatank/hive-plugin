package cn.com.pep.plugins.udf;

import cn.com.pep.plugins.common.ChapterConverJsonSingleton;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.Map;


/**
 * 描述： '1212001302125,44'  转  '1212001302125000000' 教材id，章节起始页 转 章节id
 */
public class DianduChapterIdFormat extends UDF
{
    public static String evaluate(String dataType, String key)
    {
        if ("dd_chapter".equals(dataType))
        {
            Map<String, String> ChapterIdMap = ChapterConverJsonSingleton.getInstance().getChapterIdMap();
            if (key == null)
            {
                return key;
            }
            String[] keyArr = key.split(",");
            String abc = ChapterIdMap.get(keyArr[0]);
            if (abc == null)
            {
                return key;
            }
            else
            {
                String[] itemsOfKeyArr = abc.split("\\|");
                String result = key;
                Integer flag = 100000;
                for (String i : itemsOfKeyArr)
                {
                    String[] tmpArr = i.split(",");
                    if (keyArr.length > 1 && flag >= Integer.parseInt(tmpArr[1]) && Integer.parseInt(keyArr[1]) >= Integer.parseInt(tmpArr[0]) && Integer.parseInt(keyArr[1]) <= Integer.parseInt(tmpArr[1]))
                    {
                        flag = Integer.parseInt(tmpArr[1]);
                        result = tmpArr[2];
                    }
                }
                return result;
            }
        }
        return key;
    }

    public static void main(String[] args)
    {
        System.out.println(evaluate("dd_chapter", "1321001202171,5"));
    }
}

