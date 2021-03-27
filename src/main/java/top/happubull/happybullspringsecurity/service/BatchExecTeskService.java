package top.happubull.happybullspringsecurity.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import top.happubull.happybullspringsecurity.utils.StatusManageUtils;

@Service
@Slf4j
public class BatchExecTeskService {

    @Async
    public void batchExec(String versionId) throws InterruptedException {
        // 查询所有当前版本号的请求
        log.info("开始异步执行任务,任务版本号,versionId: {}",versionId);
        StopWatch sw = new StopWatch();
        sw.start("任务_".concat(versionId));
        Thread.sleep(10000);
        sw.stop();
        log.info("异步任务执行结束,任务名:{} ,任务版本号,versionId: {}, 消耗时长: {}",sw.getLastTaskName(),versionId,sw.getTotalTimeSeconds());

        StatusManageUtils.delTask(versionId);
    }

}
