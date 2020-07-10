package weizhi.example.hiquartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PreDestroy;

@SpringBootApplication
@PropertySource("file:config/application.properties")
public class HiquartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiquartzApplication.class, args);
    }

    @Autowired
    @Qualifier("myScheduler")
    private Scheduler scheduler;

    @PreDestroy
    public void close() throws SchedulerException {
        scheduler.shutdown();
    }

}
