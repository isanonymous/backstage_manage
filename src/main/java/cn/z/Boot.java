package cn.z;

// import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@EnableTransactionManagement
// @EnableDubbo
@MapperScan("cn.z.mapper")
@ServletComponentScan({"cn.z.config"})
@SpringBootApplication
public class Boot {
  public static void main(String[] args) {
    SpringApplication.run(Boot.class, args);
  }
}
