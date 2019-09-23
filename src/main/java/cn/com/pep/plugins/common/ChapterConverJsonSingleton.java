package cn.com.pep.plugins.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 教材id转换文件单例
 */
public class ChapterConverJsonSingleton
{
    volatile private static ChapterConverJsonSingleton instance = null;
    private Map chapterIdMap = new HashMap<>();


    private ChapterConverJsonSingleton()
    {
        try
        {
            InputStream fileInput = ChapterConverJsonSingleton.class.getClassLoader().getResourceAsStream(Constants.diandu_chapter_id_mapping);
            ObjectMapper mapper = new ObjectMapper();
            chapterIdMap = mapper.readValue(fileInput, LinkedHashMap.class);
            this.setChapterIdMap(chapterIdMap);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            instance = null;
        }

    }

    public static ChapterConverJsonSingleton getInstance()
    {
        if (instance == null)
        {
            synchronized (ChapterConverJsonSingleton.class)
            {
                if (instance == null)
                {
                    instance = new ChapterConverJsonSingleton();
                }
            }
        }
        return instance;
    }

    public static void getRefresh()
    {
        instance = new ChapterConverJsonSingleton();
    }

    public Map getChapterIdMap()
    {
        return chapterIdMap;
    }

    public void setChapterIdMap(Map tbIdMap)
    {
        this.chapterIdMap = tbIdMap;
    }
}

