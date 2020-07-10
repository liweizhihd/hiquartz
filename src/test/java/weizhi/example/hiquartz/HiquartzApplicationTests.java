package weizhi.example.hiquartz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import weizhi.example.hiquartz.config.SchedulerConfig;

import javax.annotation.Resource;

@SpringBootTest
class HiquartzApplicationTests {

    @Resource
    private SchedulerConfig schedulerConfig;

    @Test
    void contextLoads() {
        System.out.println(schedulerConfig.getQuartzConfig());
    }

}
