package weizhi.example.hiquartz.bean;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import weizhi.example.hiquartz.po.PetPO;
import weizhi.example.hiquartz.service.IPetService;

import java.util.List;

/**
 * @author liweizhi
 * @date 2019/9/26 21:50
 */
@Slf4j
public class HiJob implements Job {

    @Autowired
    private IPetService petService;

    public HiJob() {

    }

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        log.info("getted param name:{}, JobDetail.Key:{}", jobDataMap.getString("name"), context.getJobDetail().getKey());
        System.out.println(petService.getAllPet());
    }
}