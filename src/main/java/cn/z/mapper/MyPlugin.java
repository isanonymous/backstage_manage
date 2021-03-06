package cn.z.mapper;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({
  @Signature(type = StatementHandler.class, method ="prepare", args = {Connection.class,Integer.class})      
})
public class MyPlugin implements Interceptor {
  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    System.out.println("-----invocation----: " + invocation);
    return null;
  }

  @Override
  public Object plugin(Object o) {
    return null;
  }

  @Override
  public void setProperties(Properties properties) {

  }
}
