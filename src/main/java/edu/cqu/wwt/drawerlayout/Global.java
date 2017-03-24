package edu.cqu.wwt.drawerlayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吴文韬 on 2017/3/23.
 */

public class Global {
    private static SqlHelper sh=null;

    public static SqlHelper getGlobalSqlHelper()
    {
        if(sh==null)
        {
            sh=new SqlHelper("*********","*******",
                    "*****","*****");
        }
        return sh;
    }

    //年级数据
    public static List<String> grades=null;

    public static List<String> GetGradesData()
    {
        return grades;
    }

    public static void SetGradeData(List<String> list)
    {
        if(grades==null)
        {
            grades = new ArrayList<String>();
        }
        for(String str:list)
        {
            grades.add(str);
        }
    }
}
