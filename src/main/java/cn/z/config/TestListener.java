package cn.z.config;

import cn.z.common.constant.TreeType;
import cn.z.ctrl.SkillTypeTreeCtrl;
import cn.z.entity.WenZhang;
import cn.z.mapper.DeptMapper;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class TestListener implements SpringApplicationRunListener {
  //必须有的构造器
  public TestListener(SpringApplication application, String[] args){
    System.out.println("   Listener:    TestListener");
  }


  @Override
  public void starting() {
    System.out.println("   Listener    starting");
  }

  @Override
  public void environmentPrepared(ConfigurableEnvironment environment) {
    System.out.println("   Listener    environmentPrepared");
  }

  @Override
  public void contextPrepared(ConfigurableApplicationContext context) {
    System.out.println("   Listener    contextPrepared");
  }

  @Override
  public void contextLoaded(ConfigurableApplicationContext context) {
    // 这里写这两句就会启动不了S.boot
    // SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
    // System.out.println("   Listener    contextLoaded" + sqlSessionFactory);
  }

  @Override
  public void started(ConfigurableApplicationContext context) {
    // testAddMappedstatement(context);
    SkillTypeTreeCtrl bean = context.getBean(SkillTypeTreeCtrl.class);
    try {
      bean.loadSkillTypeTree(TreeType.SKILL);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void testAddMappedstatement(ConfigurableApplicationContext context) {
    SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
    System.out.println("\n    Listener    started:  " + sqlSessionFactory);

    sqlSessionFactory.getConfiguration().addMapper(DeptMapper.class);
    // String sql="<script>\nDELETE FROM %s %s\n</script>";
    String sql="<script>\nSELECT * FROM wz\n</script>";
    MappedStatement.Builder builder = new MappedStatement.Builder(
            sqlSessionFactory.getConfiguration()
            ,DeptMapper.class.getName()+".testSelect"
            ,new MybatisXMLLanguageDriver().createSqlSource(
                    sqlSessionFactory.getConfiguration()
                    ,sql
                    , WenZhang.class
            )
            , SqlCommandType.SELECT
    );
    sqlSessionFactory.getConfiguration().addMappedStatement(builder.build());
  }

  @Override
  public void running(ConfigurableApplicationContext context) {
    System.out.println("   Listener    running");
  }

  @Override
  public void failed(ConfigurableApplicationContext context, Throwable exception) {
    System.out.println("   Listener  ...  failed");
    System.out.println(exception);
  }
}
