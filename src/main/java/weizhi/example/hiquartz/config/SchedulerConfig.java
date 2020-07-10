package weizhi.example.hiquartz.config;

import lombok.Data;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import weizhi.example.hiquartz.config.component.AutoWiredSpringBeanToJobFactory;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author liweizhi
 * @date 2019/11/13 16:35
 */
@Configuration
@Data
public class SchedulerConfig {
    // 配置文件路径
    @Value("${quartzConfig.location}")
    private String quartzConfig;

    // 按照自己注入的数据源自行修改
    @Qualifier("quartzDataSource")
    @Autowired
    private DataSource quartzDataSource;

    @Autowired
    private AutoWiredSpringBeanToJobFactory autoWiredSpringBeanToJobFactory;

    /**
     * 从quartz.properties文件中读取Quartz配置属性
     *
     * @return
     * @throws IOException
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        // new ClassPathResource(QUARTZ_CONFIG)
        propertiesFactoryBean.setLocation(new FileSystemResource(quartzConfig));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * JobFactory与schedulerFactoryBean中的JobFactory相互依赖,注意bean的名称
     * 在这里为JobFactory注入了Spring上下文
     *
     * @param applicationContext
     * @return
     */
    @Bean
    public JobFactory buttonJobFactory(ApplicationContext applicationContext) {
        AutoWiredSpringBeanToJobFactory jobFactory = new AutoWiredSpringBeanToJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(autoWiredSpringBeanToJobFactory);
        factory.setOverwriteExistingJobs(true);
        // 设置自行启动
        factory.setAutoStartup(true);
        // 延时启动，应用启动1秒后
        factory.setStartupDelay(1);
        factory.setQuartzProperties(quartzProperties());
        // 使用应用的dataSource替换quartz的dataSource
        factory.setDataSource(quartzDataSource);
        return factory;
    }

    @Bean(name = "myScheduler")
    public Scheduler createScheduler(SchedulerFactoryBean schedulerFactoryBean)
            throws IOException, SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
        return scheduler;
    }

}

