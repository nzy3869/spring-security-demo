package top.happubull.happybullspringsecurity.utils;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


public class StatusManageUtils {

    private static Set<String> 状态管理器 = new HashSet<>();


    public static void addTask(String 版本号){
        状态管理器.add(版本号);
    }

    public static void delTask(String 版本号){
        状态管理器.remove(版本号);
    }

    public static boolean exist(String 版本号){
        return 状态管理器.contains(版本号);
    }
}
