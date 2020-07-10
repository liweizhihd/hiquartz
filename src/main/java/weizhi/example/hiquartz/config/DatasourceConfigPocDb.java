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
@MapperScan(basePackages = {"weizhi.example.hiquartz.dao.pocdb"}, sqlSessionTemplateRef = "sqlSessionTemplatePoc")
public class DatasourceConfigPocDb {

    @Bean(name = "pocdb")
    public DataSource pocDb(Environment env) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(env.getProperty("spring.datasource.pocdb.url"));
        ds.setUsername(env.getProperty("spring.datasource.pocdb.username"));
        ds.setPassword(env.getProperty("spring.datasource.pocdb.password"));
        ds.setDriverClassName(env.getProperty("spring.datasource.pocdb.driver-class-name"));
        ds.setMaximumPoolSize(10);
        return ds;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactoryPoc(@Qualifier("pocdb") @Autowired DataSource pocDb) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // 使用本地据源, 连接本地的库
        factoryBean.setDataSource(pocDb);
        // sql写在mapper.xml中需配置
        // bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplatePoc(SqlSessionFactory sqlSessionFactoryPoc) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryPoc);
    }
}
