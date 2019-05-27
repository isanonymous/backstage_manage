package cn.z.config.injector.method;

import cn.z.config.anno.JoinOn;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * 删除全部
 * @author nieqiurong 2018/8/11 20:29.
 */
public class _MyMethod_v2 extends AbstractMethod {

  // 1.获取mapper上加了@Ljoin注解的方法
  // 2.获取实体类上加了@JoinOn的属性(如果只有一个,表名就可以确定了)
  @Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
    // 配置文件的table-prefix + entity类名
    // System.out.println(tableInfo.getTableName());  //dept; emp; mmyy_dept; mmyy_emp
    // System.out.println(tableInfo.getAllSqlSelect());  //id,name,addr; id,name,age,birthday,dept; wz_id,title,content,late_upd_date
    // entity类名
    // System.out.println(modelClass);  //cn.z.entity.Dept; cn.z.entity.Emp
    
    // 1. sql
    String sql = null;  // String sql = "select * from dept where id<3";
    // System.out.println(mapperClass);  //cn.z.mapper.DeptMapper; cn.z.mapper.EmpMapper
    //获取{类|接口}实现的所有接口
    // ArrUtil.outln(mapperClass.getInterfaces());  //com.baomidou.mybatisplus.core.mapper.BaseMapper
    // 此类所实现的接口的一个数组
    // ArrUtil.outln(mapperClass.getGenericInterfaces());  // [com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.z.entity.Dept>]
    
    for (Type t : mapperClass.getGenericInterfaces()) {
                          // ParameterizedType pt = (ParameterizedType) t;  // 强制转换为`参数化类型`【BaseMapper<cn.z.entity.Dept>】
                          // Type types[] =  ((ParameterizedType) t).getActualTypeArguments();  // 获取参数化类型中，实际类型的定义 【new Type[]{Dept.class}】
                          // ArrUtil.outln(types);  //[class cn.z.entity.Dept]
      Class clazz = (Class) ((ParameterizedType) t).getActualTypeArguments()[0];  // 获取第一个元素：Dept.class
                          // Field[] fields = clazz.getDeclaredFields();
      for (Field f : clazz.getDeclaredFields()) {
        JoinOn fieldAno;
        if ((fieldAno=f.getDeclaredAnnotation(JoinOn.class)) != null) {
          String allCol = tableInfo.getAllSqlSelect();
          String tableName = tableInfo.getTableName();
          // System.out.println(fieldAno.annotationType());  //interface cn.z.config.Join
          // System.out.println(fieldAno.toString());  //@cn.z.config.Join(col=id, joinType=L, tbl=dept)
              // sql=String.format("select %s from %s %s %s on %s.%s = %s.%s"
              //         ,allCol, tableName
              //         ,fieldAno.joinType().getVal(), fieldAno.tbl()
              //         // ,mapperClass., fieldAno.tbl()
              //         ,tableName, f.getName()
              //         ,fieldAno.tbl(), fieldAno.onCol());
          System.out.println(sql);  //select id,name,age,birthday,dept from emp left join dept on emp.dept = dept.id
        }
      }
    }
    if (sql == null) {
      return null;
    }
      // System.out.println("\n----superclass---: " + Arrays.toString(superclass));
      // String sql = "";
    // }
    // 2. 不知道什么意思,照着抄就行了
    SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
    String method = "selectDept3";  // mapper 接口方法名一致
    // 3. addMappedStatement
    return this.addSelectMappedStatement(mapperClass, method, sqlSource,Map.class,tableInfo);
  }

  /*@Override
  public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        *//* 执行 SQL ，动态 SQL 参考类 SqlMethod *//*
    String sql = "delete from " + tableInfo.getTableName();
        *//* mapper 接口方法名一致 *//*
    String method = "deleteAll";
    SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
    return this.addDeleteMappedStatement(mapperClass, method, sqlSource);
  }*/
}

