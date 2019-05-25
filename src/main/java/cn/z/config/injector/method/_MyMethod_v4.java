package cn.z.config.injector.method;

import cn.z.config.TblRegister;
import cn.z.config.anno.JoinAndSelect;
import cn.z.config.anno.slave.ForJoin;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 删除全部
 * @author nieqiurong 2018/8/11 20:29.
 */
public class _MyMethod_v4 extends AbstractMethod {

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

      // ParameterizedType pt = (ParameterizedType) t;  // 强制转换为`参数化类型`【BaseMapper<cn.z.entity.Dept>】
      // Type types[] =  ((ParameterizedType) t).getActualTypeArguments();  // 获取参数化类型中，实际类型的定义 【new Type[]{Dept.class}】
      // ArrUtil.outln(types);  //[class cn.z.entity.Dept]

      Object selectCol;
      
      Method[] methods = mapperClass.getDeclaredMethods();  //如:selectList()...
      for (Method method : methods) {
        // ArrUtil.outln(method.getDeclaredAnnotations());  //[@...ForLjoin(value=[@...Ljoin(tblName=, value=[233]), @...Ljoin(tblName=, value=[2334])]), @...Rjoin(tblName=, value=555)]
        for (Annotation anno : method.getDeclaredAnnotations()) {
          ForJoin fj;
          JoinAndSelect jas;
          if ((fj=getAnnotation(anno, ForJoin.class))!=null) {
            try {
              Object invoke = fj.value().getMethods()[0].invoke(anno);  //{LIR}join[] value();
              if (invoke.getClass().isArray()) {
                for (Object obj : (Object[]) invoke) {
                  selectCol = obj.getClass().getMethod("value").invoke(obj);
                }
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          } else if ((jas=getAnnotation(anno, JoinAndSelect.class))!=null) {
            String joinType = jas.joinType().getVal();
            // Object value = null;
            try {
              selectCol = anno.annotationType().getMethod("value").invoke(anno);
            } catch (Exception e) {
              e.printStackTrace();
            } 
          }
        }
      }
      TblRegister.addMap(modelClass);
      // Field[] fields = clazz.getDeclaredFields();
//      for (Field f : modelClass.getDeclaredFields()) {
//        JoinOn fieldAno;
//        if ((fieldAno=f.getDeclaredAnnotation(JoinOn.class)) != null) {
//          // String currentNamespace = builderAssistant.getCurrentNamespace();
//          // String tableName = tableInfo.getTableName();
//          // String tablePrefix = tableName.substring(0, tableName.indexOf("_")+1);
//          String tablePrefix=DBConfig.tablePrefix;
//          String tableName=tablePrefix + modelClass.getSimpleName();
//
//          String[] refTblInfo = fieldAno.tbl().split("(?i)AS");
//          String refTbl = !StringUtils.isEmpty(fieldAno.tbl()) ? refTblInfo[0].trim() : tablePrefix+f.getType().getSimpleName();
//          String refCol =fieldAno.onCol();
//          // String foreignKeyCol =f.getName();
//          // String allCol = tableInfo.getAllSqlSelect();
//
//          TableName annotation = modelClass.getAnnotation(TableName.class);
//          String tblName = annotation !=null ? annotation.value() : DBConfig.tablePrefix+modelClass.getSimpleName();
//          TblRegister.addMap(tblName,f.getName(),refTbl,refCol);
//          TblRegister.addMap(f.getType());
//          
//          // System.out.println(fieldAno.annotationType());  //interface cn.z.config.Join
//          // System.out.println(fieldAno.toString());  //@cn.z.config.Join(col=id, joinType=L, tbl=dept)
//          sql=String.format("select %s from %s %s %s on %s.%s = %s.%s"
//                  ,allCol, tableName
//                  ,fieldAno.joinType().getVal(), fieldAno.tbl()
//                  // ,mapperClass., fieldAno.tbl()
//                  ,tableName, f.getName()
//                  ,fieldAno.tbl(), fieldAno.onCol());
//        }
//      }
    System.out.println(sql);  //select id,name,age,birthday,dept from emp left join dept on emp.dept = dept.id
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

  private static <A extends Annotation> A getAnnotation(Annotation target, Class<A> anno) {
    return (A)target.annotationType().getAnnotation(anno);
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

