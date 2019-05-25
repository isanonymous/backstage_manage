package cn.z.config;

import cn.z.config.filter.LoginInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

//使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {
  /** @link # https://blog.csdn.net/u010028869/article/details/86563382*/
  // converters-->jackson2HttpMessageConverter-->objectMapper-->simpleModule-->addSerializer()
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
    
    objectMapper.registerModule(simpleModule);
    jackson2HttpMessageConverter.setObjectMapper(objectMapper);
    converters.add(jackson2HttpMessageConverter);
  }
  
  @Override
  protected void addViewControllers(ViewControllerRegistry registry) {
    // super.addViewControllers(registry);
    //浏览器发送 /atguigu 请求来到 success
    registry.addViewController("/page").setViewName("page");
  }

  @Override
  protected void addInterceptors(InterceptorRegistry registry) {
    //super.addInterceptors(registry);
    //静态资源；  *.css , *.js
    //SpringBoot已经做好了静态资源映射
    // registry.addInterceptor(new TestInterceptor()).addPathPatterns("/**")
    //         .excludePathPatterns("/index.html","/","/user/login");

    // registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/doLogin");
            // .excludePathPatterns("/index.html","/","/user/login");
  }

  //将方法的返回值添加到容器中；容器中这个组件默认的id就是方法名
  // @Bean
  // public Fu helloService02() {
  //   System.out.println("配置类@Bean给容器中添加组件了...");
  //   return new Fu();
  // }
}

