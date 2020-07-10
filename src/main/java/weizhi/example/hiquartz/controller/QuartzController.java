package weizhi.example.hiquartz.controller;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weizhi.example.hiquartz.bean.HiJob;

import java.util.Date;

/**
 * @author liweizhi
 * @date 2019/11/13 16:23
 */
@RestController
@RequestMapping("quartz")
@Slf4j
public class QuartzController {

    private static final String GROUP = "group1";

    @Autowired
    @Qualifier("myScheduler")
    private Scheduler scheduler;

    @GetMapping("meta")
    public SchedulerMetaData getMetaData() throws SchedulerException {
        return scheduler.getMetaData();
    }

    @GetMapping("/exist/{id}")
    public boolean existJob(@PathVariable("id") String id) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(id, GROUP);
        return scheduler.checkExists(jobKey);
    }

    @GetMapping("/delete/{id}")
    public SchedulerMetaData deleteJob(@PathVariable("id") String id) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(id, GROUP);
        scheduler.deleteJob(jobKey);
        return scheduler.getMetaData();
    }

    @GetMapping("/add/{id}")
    public SchedulerMetaData addJob(@PathVariable("id") String id) throws SchedulerException {

        JobKey jobKey = JobKey.jobKey(id, GROUP);
        JobDetail job = JobBuilder.newJob(HiJob.class)
                .withIdentity(jobKey)
                .usingJobData("name", "=============axiba================")
                .build();

        // Trigger the job to run now, and then repeat every 40 seconds
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey(id, GROUP))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(1)
                        .repeatForever()
                        .withMisfireHandlingInstructionNextWithRemainingCount()
                )
                .startNow()
//                .startAt(new Date(System.currentTimeMillis() + 5000))
                /*.withSchedule(cronSchedule("0/20 * * ? * *")
//                        .withMisfireHandlingInstructionDoNothing()
                )*/
                .build();
        System.err.println("before scheduleJob =============" + new Date());
        scheduler.scheduleJob(job, trigger);
        /*Trigger triggerRightNow = TriggerBuilder.newTrigger()
                .withIdentity(TriggerKey.triggerKey(id + "::triggerRightNow", GROUP))
                .forJob(jobKey)
                .startNow()
                .build();
        scheduler.scheduleJob(triggerRightNow);*/
        System.err.println("after scheduleJob =============" + new Date());
        return scheduler.getMetaData();
    }

    @GetMapping("/pause/{id}")
    public void pauseJob(@PathVariable("id") String id) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(id, GROUP);
        scheduler.pauseJob(jobKey);
    }

    @GetMapping("/resume/{id}")
    public void resumeJob(@PathVariable("id") String id) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(id, GROUP);
        scheduler.resumeJob(jobKey);
    }

    @GetMapping("/detail/{id}")
    public void detailJob(@PathVariable("id") String id) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(id, GROUP);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        System.out.println("read job detail");
    }

    public static void main(String[] args) {
        try {
            errorTest();
        } catch (Exception e) {
            log.error("error!", e);
            System.out.println(e.getMessage());
            System.out.println(e.getMessage().length());
        }
    }

    private static void errorTest(){
        System.out.println(1 / 0);
    }
}
