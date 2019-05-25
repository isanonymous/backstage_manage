package cn.z;

import cn.z.common.simplify.DBMap;
import cn.z.common.util.ArrUtil;
import cn.z.common.util.JsonUtil;
import cn.z.config.TblRegister;
import cn.z.config.anno.FromTbl;
import cn.z.config.anno.JoinAndSelect;
import cn.z.config.anno.Ljoin;
import cn.z.config.anno.slave.ForJoin;
import cn.z.entity.SkillType;
import cn.z.entity.WenZhang;
import cn.z.mapper.DeptMapper;
import cn.z.mapper.EmpMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Testt {
  public static void main(String[] args) throws UnsupportedEncodingException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    MyEnum one = MyEnum.ONE;
    System.out.println(one.valueOf(MyEnum.class,"TWO"));  //TWO

    // bool2jsonTest();
    // System.out.println(URLDecoder.decode("%7B%22total%22:0,%22curPage%22:1,%22pageSize%22:10%7D","utf-8"));
    // 结果: {"total":0,"curPage":1,"pageSize":10}

    // getStrTest();
    System.out.println(1 < 2L);
    Method m = DeptMapper.class.getMethod("selectDept3", null);
    // ArrUtil.outln(m.getDeclaredAnnotations());  //[@cn.z.config.Ljoin(selectCol=[32])]
    Ljoin anno = m.getAnnotation(Ljoin.class);
    // System.out.println(anno.getClass());  // class com.sun.proxy.$Proxy1
    // System.out.println(anno.annotationType());  // interface cn.z.config.Ljoin
    // ArrUtil.outln(anno.annotationType().getDeclaredAnnotations());  //[@...Target(value=[METHOD]), @...Retention(value=RUNTIME), @cn.z.config.JoinAndSelect(value=[], selectCol=[], joinType=L)]

    TblRegister.addMap("sheng","shiId","shi","shiId");
    TblRegister.Tbl sheng = TblRegister.getTbl("sheng");
    TblRegister.Tbl shi = TblRegister.getTbl("shi");

    String[] as = "dept as d".split("(?i)AS");
    // ArrUtil.outln(as);

    StringBuffer sb = new StringBuffer();
    sb.append("\n233");
    sb.deleteCharAt(0);
    // System.out.println(sb);
    
    String str=new String("32423");
    // System.out.println("\n-------: " + str);

    // testSetRepetition();
    // generateEltree();
    // testBeanUtils4Map();
    // testQueryWrapper();

    // spel_01();
    Method selectDept3 = EmpMapper.class.getMethod("selectDept3");
    // ArrUtil.outln(selectDept3.getDeclaredAnnotations());  //ForLjoin,Rjoin,Ijoin
    // Ljoin ljoin = selectDept3.getAnnotation(Ljoin.class);
    List<Annotation> splitResult = new ArrayList<>();
    Annotation[] annos = selectDept3.getAnnotations();
    for (Annotation annotation : annos) {
      JoinArrayCase(annotation,splitResult);
      JoinCase(annotation, splitResult);
      fromTblCase(annotation, splitResult);
    }
    System.out.println(splitResult);
    splitResult.sort(Comparator.comparingInt(
            value -> {
              try {
                return Integer.parseInt(value.annotationType().getMethod("order").invoke(value).toString());
              } catch (Exception e) {
                e.printStackTrace();
                return -1;
              }
            }
    ));
    System.out.println(splitResult);

    // testListDefaultValue();
    // testStringBufferInsert();
    Comparator<Long> comparator = Comparator.comparingLong(
            value -> value  );
    // TreeSet<Long> treeSet = new TreeSet<>(comparator);
    TreeSet<Long> treeSet = new TreeSet<>((o1, o2) -> (int)(o1-o2));
    treeSet.addAll(Arrays.asList(-1L, 2L, 4L, 75L, 43L, 1L));
    System.out.println(treeSet);

    testKeBianCan();

    // System.out.println(new String("鐧婚檰鎴愬姛".getBytes(Charset.forName("gbk")), StandardCharsets.UTF_8));
    byte[] bytes = "登陆成功".getBytes("gbk");
    System.out.println(new String(bytes, "UTF-8"));

    String cn = "中文";
    System.out.println("args= ["+cn+"中文");

    TreeSet<Long> longSet = new TreeSet<>();
    treeSet.addAll(Arrays.asList(1127721564813471746L,1127723018773147650L,1127724979287306242L,1127732651742986241L,1130674510232674306L));
    if (treeSet.contains(1127732651742986241L)) {
      System.out.println("  1127732651742986241");
    }
  }

  private static void testKeBianCan(int... data) {
    Arrays.toString(data);
    System.out.println(data.length==0);
  }

  private static void testStringBufferInsert() {
    StringBuffer sb22 = new StringBuffer();
    sb22.append("3");
    sb22.insert(0, "11");
    sb22.insert(1, "_911_");
    System.out.println(sb22);
  }

  private static void testListDefaultValue() {
    List<List> classifyNodeByLevel = new ArrayList<>(3);
    classifyNodeByLevel.add(new ArrayList());
    System.out.println(classifyNodeByLevel);  // [[]]
  }

  private static void fromTblCase(Annotation annotation, List<Annotation> splitResult) {
    if (annotation.annotationType()== FromTbl.class){
      splitResult.add(annotation);
    }
  }

  private static void JoinCase(Annotation annotation, List<Annotation> splitResult) {
    if (annotation.annotationType().isAnnotationPresent(JoinAndSelect.class)){
      // System.out.println(annotation);  // @cn.z.config.anno.Ijoin(value=*, tblName=, order=3)
      splitResult.add(annotation);
    }
  }

  private static void JoinArrayCase(Annotation annotation,List<Annotation> splitResult) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    if(annotation.annotationType().isAnnotationPresent(ForJoin.class)){
      Object value = annotation.annotationType().getMethod("value").invoke(annotation);
      // ArrUtil.outln(value);
      if (value.getClass().isArray()) {
        for (Object obj : (Object[]) value) {
          // System.out.println(obj);  // @cn.z.config.anno.Ljoin(value=*, tblName=, order=4)
          splitResult.add((Annotation) obj);
        }
      }
    }
  }

  private static void spel_01() {
    ExpressionParser parser = new SpelExpressionParser();
    Expression expression = parser.parseExpression("6+2");
    Integer result = (Integer) expression.getValue();
    System.out.println("result:" + result);
  }

  private static void testQueryWrapper() {
    QueryWrapper<WenZhang> qw =new QueryWrapper<>();
    qw.eq("wz_id",233L);
    System.out.println(qw.getSqlSegment());           //wz_id = #{ew.paramNameValuePairs.MPGENVAL1}
    System.out.println(qw.getCustomSqlSegment());     //WHERE wz_id = #{ew.paramNameValuePairs.MPGENVAL1}
    System.out.println(qw.getParamNameValuePairs());  //{MPGENVAL1=233}
  }

  private static void testBeanUtils4Map() throws IllegalAccessException, InvocationTargetException {
    WenZhang wz = new WenZhang();
    HashMap<String, Object> hashMap = new HashMap<>();
    hashMap.put("wzId",12315L);
    hashMap.put("title","titile");
    hashMap.put("content","content");
    hashMap.put("lateUpdDate",new Date());
    BeanUtils.copyProperties(wz,hashMap);
    System.out.println(wz);
  }

  private static void generateEltree() {
    // skill_type_id  skill_type_name  parent
    // SkillType parent = new SkillType();
    // parent.setChildren(new ArrayList<>());
    SkillType type = new SkillType(1L, "中国", 0L, null, null, null, null);
    SkillType type1 = new SkillType(2L, "广东", 1L, null, null, null, null);
    SkillType type2 = new SkillType(3L, "广州", 2L, null, null, null, null);
    SkillType type3 = new SkillType(4L, "广西", 1L, null, null, null, null);
    ArrayList<SkillType> objects = new ArrayList<>();
    ArrayList<SkillType> resultList = new ArrayList<>();
    objects.add(type);
    objects.add(type1);
    objects.add(type2);
    objects.add(type3);
    Map<Long, SkillType> map = new TreeMap<>();
    for (SkillType object : objects) {
      map.put(object.getSkillTypeId(), object);
      System.out.println(object.getSkillTypeName());
      if (object.getParent() == 0) {  //根节点
        // object.setChildren(new ArrayList<>());
        
        resultList.add(object);
      } else {
        if (map.containsKey(object.getParent())) {  //map中有它的parent
          SkillType skillType = map.get(object.getParent());
          if (skillType.getChildren() == null) {
            skillType.setChildren(new ArrayList<>());
          }
          skillType.addChild(object);
        }
        
        // delList.add(object);
      }
      
    }
    // objects.removeAll(delList);
    System.out.println(JsonUtil.objectToJson(resultList));
  }

  /** Set集合元素的重复*/
  private static void testSetRepetition() {
    HashSet<Object> set = new HashSet<>();
    set.add(null);
    set.add(null);
    set.add("23543");
    set.add("23543");
    System.out.println(set);  //[null, 23543]
  }

  /** boolean转成json是不带""的(:true)*/
  private static void bool2jsonTest() {
    HashMap<String, Object> hashMap = new HashMap<>();
    hashMap.put("state", true);
    System.out.println(JsonUtil.objectToJson(hashMap));  //{"state":true}

    hashMap.put("wz_id", 111L);
    hashMap.put("title", "beanu");
    hashMap.put("content", "beanu");
  }

  /** 获取str成功*/
  private static void getStrTest() {
    DBMap dbMap = new DBMap();
    Long l = new Long(2);
    dbMap.put("str", l);
    System.out.println(dbMap.getStr("str").getClass());  //class java.lang.String
  }
}
