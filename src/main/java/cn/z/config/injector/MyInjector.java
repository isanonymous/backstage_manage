package cn.z.config.injector;

import cn.z.config.injector.method.TryCommon;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import org.springframework.stereotype.Component;

import java.util.List;
// import com.baomidou.samples.injector.methods.DeleteAll;

/**
 * 自定义Sql注入
 * @author nieqiurong 2018/8/11 20:23.
 */
@Component
public class MyInjector extends DefaultSqlInjector {

  @Override
  public List<AbstractMethod> getMethodList() {
    System.out.println("----myInjector---: " + MyInjector.class);
    List<AbstractMethod> methodList = super.getMethodList();
    //增加自定义方法
    // methodList.add(new MyMethod());
    // methodList.add(new SelectListPageMethod());
    methodList.add(new TryCommon());
    return methodList;
  }
}