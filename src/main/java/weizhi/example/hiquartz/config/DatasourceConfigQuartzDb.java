package weizhi.example.hiquartz.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * @Auther: liweizhi
 * @Date: 2019/6/24 14:53
 * @Description:
 */
@Configuration
// basePackages指定mapper的文件夹
@MapperScan(basePackages = {"weizhi.example.hiquartz.dao.quartzdb"}, sqlSessionTemplateRef = "sqlSessionTemplatequartz")
public class DatasourceConfigQuartzDb {

    @Bean(name = "quartzDataSource")
    public DataSource quartzDb(Environment env) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(env.getProperty("spring.datasource.quartzdb.url"));
        ds.setUsername(env.getProperty("spring.datasource.quartzdb.username"));
        ds.setPassword(env.getProperty("spring.datasource.quartzdb.password"));
        ds.setDriverClassName(env.getProperty("spring.datasource.quartzdb.driver-class-name"));

        ds.setMaximumPoolSize(10);
        return ds;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactoryquartz(@Qualifier("quartzDataSource") @Autowired DataSource quartzDb) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(quartzDb);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplatequartz(SqlSessionFactory sqlSessionFactoryquartz) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryquartz);
    }
}
