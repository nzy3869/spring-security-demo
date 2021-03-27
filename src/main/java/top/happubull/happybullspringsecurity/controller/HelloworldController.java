package top.happubull.happybullspringsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.happubull.happybullspringsecurity.model.User;
import top.happubull.happybullspringsecurity.service.BatchExecTeskService;
import top.happubull.happybullspringsecurity.utils.StatusManageUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
public class HelloworldController {

    private static final SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    @Autowired
    private BatchExecTeskService service;



    @RequestMapping("/")
    public String hello(){
        Set<User> userSet = new HashSet<>();
        return "hello security "+SDF_YYYY_MM_DD.format(new Date());
    }

    @RequestMapping("/task")
    public String doExecTask(String versionId) throws InterruptedException {

        log.info("收到执行任务请求, versionId: {}",versionId);
        if (StatusManageUtils.exist(versionId)){
            return String.format("任务%s已经在执行了",versionId);
        }

        StopWatch sw = new StopWatch();
        sw.start("执行任务");

        StatusManageUtils.addTask(versionId);
        service.batchExec(versionId);

        sw.stop();
        log.info("同步方法消耗时间: {} ms",String.valueOf(sw.getTotalTimeMillis()));
        return "开始执行任务,versionId: ".concat(versionId);
    }

}
