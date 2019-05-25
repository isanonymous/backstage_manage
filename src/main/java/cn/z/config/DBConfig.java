package cn.z.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DBConfig {
  public static String tablePrefix;
  @Value("${mybatis-plus.global-config.db-config.table-prefix:}")
  public void tablePrefix(String tablePrefix) {
    DBConfig.tablePrefix= !StringUtils.isEmpty(tablePrefix) ? tablePrefix : "";
  }
  
  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource druid() {
    return new DruidDataSource();
  }

  @Bean
  public PlatformTransactionManager txManager(DataSource druid) {
    return new DataSourceTransactionManager(druid);
  }
}
