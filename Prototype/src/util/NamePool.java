package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyiqing on 16/12/18.
 * Name pool to store names
 * 姓名池，用于生成随机名字
 */
public class NamePool {

    public static List<String> nameList;

    static {
        generateNameList();
    }

    //TODO 可换成文件读取
    private static void generateNameList(){
        nameList = new ArrayList<String>();
        nameList.add("Jaina");
        nameList.add("Rexxar");
        nameList.add("Uther");
        nameList.add("Garrosh");
        nameList.add("Malfurion");
        nameList.add("Gul'dan");
        nameList.add("Thrall");
        nameList.add("Anduin");
        nameList.add("Valeera");
    }
}
