package cn.z.config.injector.method;

import cn.z.config.TblRegister;
import cn.z.config.anno.FromTbl;
import cn.z.config.anno.JoinAndSelect;
import cn.z.config.anno.slave.ForJoin;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 删除全部
 * @author nieqiurong 2018/8/11 20:29.
 */
public class _MyMethod_v6 extends AbstractMethod {

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
    StringBuffer sql = new StringBuffer();  // String sql = "select * from dept where id<3";
    // System.out.println(mapperClass);  //cn.z.mapper.DeptMapper; cn.z.mapper.EmpMapper
    //获取{类|接口}实现的所有接口
    // ArrUtil.outln(mapperClass.getInterfaces());  //com.baomidou.mybatisplus.core.mapper.BaseMapper
    // 此类所实现的接口的一个数组
    // ArrUtil.outln(mapperClass.getGenericInterfaces());  // [com.baomidou.mybatisplus.core.mapper.BaseMapper<cn.z.entity.Dept>]

      // ParameterizedType pt = (ParameterizedType) t;  // 强制转换为`参数化类型`【BaseMapper<cn.z.entity.Dept>】
      // Type types[] =  ((ParameterizedType) t).getActualTypeArguments();  // 获取参数化类型中，实际类型的定义 【new Type[]{Dept.class}】
      // ArrUtil.outln(types);  //[class cn.z.entity.Dept]
    TblRegister.addMap(modelClass);
    Map<String, TblRegister.Tbl> registInfo = TblRegister.registInfo;
    String prevTbl = null;

      StringBuffer selectCol=new StringBuffer();
    Map<String,String> joinTypeMap = new LinkedHashMap<>();  //k:表名, v:join类型

    String currMethod=null;
    Method[] methods = mapperClass.getDeclaredMethods();  //如:selectList()...
      for (Method method : methods) {
        currMethod =method.getName();
        // ArrUtil.outln(method.getDeclaredAnnotations());  //[@...ForLjoin(value=[@...Ljoin(tblName=, value=[233]), @...Ljoin(tblName=, value=[2334])]), @...Rjoin(tblName=, value=555)]
        for (Annotation anno : method.getDeclaredAnnotations()) {
          ForJoin fj;
          JoinAndSelect jas;
          if (anno.annotationType()== FromTbl.class) {
            String value = ((FromTbl)anno).value();
            selectCol.append(",").append(value);
        // 多个相同{LIR}join注解的情况  
          } else if ((fj = getAnnotation(anno, ForJoin.class)) != null) {
            try {
              Object invoke = anno.getClass().getMethod("value").invoke(anno);  //{LIR}join[] value();
              if (invoke.getClass().isArray()) {
                for (Object obj : (Object[]) invoke) {  //{LIR}join
                  // String joinType = obj.getClass().getDeclaredAnnotation(JoinAndSelect.class).joinType().getVal();
                  // 两张表的join类型
                  String joinType = ((Annotation)obj).annotationType().getDeclaredAnnotation(JoinAndSelect.class).joinType().getVal();
                  // @{LIR}join的tblName()
                  String tblName = obj.getClass().getMethod("tblName").invoke(obj).toString();
                            // Map<String, TblRegister.Tbl> registInfo = TblRegister.registInfo;
                  if (StringUtils.isEmpty(tblName)) {
                    String getTblName= prevTbl != null?prevTbl:getTblName(modelClass, tableInfo);
                    //获取当前modelClass的表名, 通过表名再获取join的表
                    TblRegister.Tbl tblByFk = TblRegister.getTbl(getTblName).getUniqueForeignTbl();
                    String as = StringUtils.isEmpty(tblByFk.getAlias()) ? "" : " AS " + tblByFk.getAlias();
                    tblName = tblByFk.getName()+as;
                  }
                  prevTbl = tblName;
                  joinTypeMap.put(tblName, joinType);
                  selectCol.append(",")
                          .append(obj.getClass().getMethod("value").invoke(obj));
                }
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
        // {LIR}join只有一个的情况  
          } else if ((jas = getAnnotation(anno, JoinAndSelect.class)) != null) {  //{LIR}join;
            String joinType = jas.joinType().getVal();
            String tblName = null;
            try {
              tblName = anno.getClass().getMethod("tblName").invoke(anno).toString();
              if (StringUtils.isEmpty(tblName)) {  //{LIR}join的`tblName`属性为默认值;
                //获取表名, 通过表名再获取join的表
                String getTblName = prevTbl != null?prevTbl:getTblName(modelClass, tableInfo);
                TblRegister.Tbl tblByFk = TblRegister.getTbl(getTblName).getUniqueForeignTbl();
                String as = StringUtils.isEmpty(tblByFk.getAlias()) ? "" : " AS " + tblByFk.getAlias();
                tblName = tblByFk.getName()+as;
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
            joinTypeMap.put(tblName,joinType);
            try {
              selectCol.append(",")
                      .append(anno.annotationType().getMethod("value").invoke(anno));
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
        if (selectCol.length()>0) {
          sql.append("<script>select ");
          sql.append(selectCol.deleteCharAt(0));
          sql.append("\n from ");
          // TableName tableName = modelClass.getDeclaredAnnotation(TableName.class);
          // selectCol.append(tableName !=null?tableName.value():tableInfo.getTableName());
          sql.append(getTblName(modelClass, tableInfo));

          StringBuffer joinOn = produceJoinOn(joinTypeMap, getTblName(modelClass, tableInfo));
          System.out.println("\n        joinOn:  " + sql.append(joinOn));
          sql.append(" <where> ${ew.sqlSegment} </where></script>");
        }
        if (sql.length()>0)break;

        // selectCol.setLength(0);
        // sql.setLength(0);
        // prevTbl=null;
        // if (modelClass.getSimpleName().equalsIgnoreCase("dept") || mapperClass.getSimpleName().equalsIgnoreCase("emp"))
        //   System.out.println(modelClass.getSimpleName()+"\n-------joinTypeMap: " + joinTypeMap);
        // joinTypeMap.clear();
      }
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
    if (sql == null || sql.toString().trim().length()==0) {
      return null;
    }
    // System.out.println("\n----superclass---: " + Arrays.toString(superclass));
    // String sql = "";
    // }
    // 2. 不知道什么意思,照着抄就行了
    SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql.toString(), modelClass);
    String method =currMethod;  // String method = "selectDept3";  // mapper 接口方法名一致
    // 3. addMappedStatement
    return this.addSelectMappedStatement(mapperClass, method, sqlSource,Map.class,tableInfo);
  }

  private StringBuffer produceJoinOn(Map<String, String> joinTypeMap, String tblName) {
    StringBuffer sb = new StringBuffer();
    for (Iterator<String> it = joinTypeMap.keySet().iterator(); it.hasNext(); ) {
        String next = it.next();  //tbl_shi
        String joinType = joinTypeMap.get(next);  //tbl_shi

      // Map<String, TblRegister.Tbl> registInfo = TblRegister.registInfo;
      TblRegister.Tbl tbl = TblRegister.getTbl(tblName);  //tbl_sheng
      Map<String, TblRegister.Tbl> mapInfo = tbl.getMapInfo();  //tbl_sheng的所有外键关系
      for(Iterator<Map.Entry<String, TblRegister.Tbl>> iterator = mapInfo.entrySet().iterator();iterator.hasNext();) {
        Map.Entry<String, TblRegister.Tbl> entry = iterator.next();
        if (entry.getValue().getName().equalsIgnoreCase(next)) {
          sb.append("\n"+joinType+" "+next+" on "+tbl.getName()+"."+entry.getKey()+"="+next+"."+entry.getValue().getPrimaryKey());
          tblName = entry.getValue().getName();
        }
      }
    }
    return sb;
  }

  private static String getTblName(Class<?> obj, TableInfo tableInfo) {
    TableName tableName = obj.getDeclaredAnnotation(TableName.class);
    // 注解上的表名, 优先于TableInfo的
    return tableName !=null?tableName.value():tableInfo.getTableName();
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

