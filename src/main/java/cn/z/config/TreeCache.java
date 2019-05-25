package cn.z.config;

import cn.z.common.simplify.SpUtil;
import cn.z.entity.SkillType;
import cn.z.service.SkillTypeService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class TreeCache {
  private static SkillTypeService skillTypeService = SpUtil.getBean(SkillTypeService.class);
  public static final Map<Integer, List<SkillType>> typeAndTreeDataMap = new ConcurrentHashMap<>();
  
  public static final Map<Integer,Map<Long, SkillType>> typeAndAllNodeMap = new ConcurrentHashMap<>();

  /**
   * 添加或者修改节点(不支持拖拽节点)
   * @param treeType 树的类型
   * @param skillType 节点对象
   * @return 参考:java.util.Map.put(K , V)
   */
  public static SkillType addOrUpdNode(int treeType, SkillType skillType) {
    Map<Long, SkillType> typeMap = typeAndAllNodeMap.get(treeType);
    if (skillType.getParent() != 0) {
      SkillType parent = typeMap.get(skillType.getParent());
      if (parent.getChildren() == null || parent.getChildren().isEmpty()) {  //父节点之前没有子节点
        parent.setIsLeaf((byte) 0);
        skillTypeService.updateById(parent);
        if (parent.getChildren() == null) {
          parent.setChildren(new ArrayList<>());
        }
      }
      int temp=-1;
      List<SkillType> children = parent.getChildren();
      for (int i = 0; i< children.size(); i++) {
        SkillType st = children.get(i);
        if (st.getSkillTypeId().longValue() == skillType.getSkillTypeId()) {
          temp=i;
        }
      }
      if (temp != -1) {
        children.remove(temp);
      }
      parent.addChild(skillType);  //父节点中添加新节点
      // parent.addChild(skillType);  //父节点中添加新节点
      
      LinkedList<String> fullParent = new LinkedList<>();
      if (parent.getFullParent()!=null) {
        fullParent.addAll(parent.getFullParent());
      }
      fullParent.add(parent.getSkillTypeId().toString());
      skillType.setFullParent(fullParent);
    } else {
      typeAndTreeDataMap.get(treeType).add(skillType);  //树数据中添加新节点
    } 
    return typeMap.put(skillType.getSkillTypeId(), skillType);  //添加到allNode缓存
  }

  public static List<SkillType> getEltreeData(int treeType){
    return typeAndTreeDataMap.get(treeType);
  }

  public static List<SkillType> getEltreeData(List<SkillType> li, int treeType){
    List<SkillType> thisTreeData = typeAndTreeDataMap.get(treeType);
    if (thisTreeData != null) {
      return thisTreeData;
    } else {  //只会在启动时
      return parseListToEltree(li, treeType);
    } 
  }

  private static List<SkillType> parseListToEltree(List<SkillType> li, int treeType) {
    // Comparator<Long> comparator =(o1, o2) -> (int)(o1-o2); // Comparator.comparingLong(  value -> value  );
    Comparator<Long> comparator =Comparator.comparingLong(value -> value);
    TreeMap<Long, SkillType> leafTreeMap=new TreeMap(comparator);  //所有的子节点
    TreeMap<Long, SkillType> parentTreeMap=new TreeMap(comparator);  //所有的父节点
    TreeMap<Long, SkillType> cacheMap=new TreeMap(comparator);  //所有的节点
    ArrayList<SkillType> resultList = new ArrayList<>();
    ArrayList<SkillType> topLeafList = new ArrayList<>();

    for (SkillType object : li) {  //把数据存到map中,并按isLeaf区分
      cacheMap.put(object.getSkillTypeId(), object);

      if (object.getIsLeaf() == 1) {
        if (object.getParent() == 0) {
          topLeafList.add(object);
          continue;
        }
        leafTreeMap.put(object.getSkillTypeId(), object);
      } else {
        parentTreeMap.put(object.getSkillTypeId(), object);
        if (object.getParent() == 0) {
          resultList.add(object);
        }
      }
    }
    TreeCache.typeAndAllNodeMap.put(treeType, cacheMap);  //把所有的节点添加到全局

    Set<Long> keySet = parentTreeMap.keySet();

    for (SkillType object : li) {  //为所有父节点添加子节点
      if (keySet.contains(object.getParent())) {
        SkillType skillType = parentTreeMap.get(object.getParent());
        if (skillType.getChildren() == null) {  //保证父节点有children
          skillType.setChildren(new ArrayList<>());
        }
        skillType.addChild(object);
      }
    }

    for (SkillType object : resultList) {  //从根节点开始遍历,为所有父节点添加所有上级id
      addFullParent(object);
    }
    resultList.addAll(topLeafList);

    for (SkillType object : leafTreeMap.values()) {  //为所有子节点添加所有上级id
      // getFullParentId(cacheMap, keySet, object);
      SkillType parent = cacheMap.get(object.getParent());
      LinkedList<String> fullParent = new LinkedList<>();
      if (parent.getFullParent()!=null) {
        fullParent.addAll(parent.getFullParent());
      }
      fullParent.add(parent.getSkillTypeId().toString());
      object.setFullParent(fullParent);
    }

    // TreeCache.typeAndTreeDataMap.put(li.get(0).getType(), resultList);
    TreeCache.typeAndTreeDataMap.put(treeType, resultList);  //把树数据添加到全局
    return resultList;
  }

  private static void addFullParent(SkillType parent) {
    // SkillType parent=object;
    for (SkillType child : parent.getChildren()) {  //某个根节点
      LinkedList<String> fullParent = new LinkedList<>();
      // SkillType parent = cacheMap.get(child.getParent());
      if (parent.getFullParent()!=null) {
        fullParent.addAll(parent.getFullParent());  //获取parent,再获取其FullParent
      }
      fullParent.add(parent.getSkillTypeId().toString());  //获取parent的id
      // fullParent.add(object.getSkillTypeId().toString());
      child.setFullParent(fullParent);
      if (child.getChildren() != null) {
        addFullParent(child);
      }
    }
  }

  private static void delNodeAndChildNode(SkillType skillType, List<Long> ids) {  //只是为了递归...
    List<SkillType> children = skillType.getChildren();
    if (children != null && children.size() > 0) {
      for (SkillType child : children) {
        delNodeAndChildNode(child, ids);
      }
    }
    // skillTypeService.removeById(skillType.getSkillTypeId());
    ids.add(skillType.getSkillTypeId());
    // return ids;
  }

  /**删除节点为子/父
   * 删除节点后,其父节点的变化
   */
  public static void delNode(Integer treeType, Long skillTypeId) {
    Map<Long, SkillType> typeMap = typeAndAllNodeMap.get(treeType);
    SkillType skillType = typeMap.get(skillTypeId);
    
    SkillType parent = typeMap.get(skillType.getParent());
    parent.removeChild(skillType);//获取parent,再从Parent中删除
    if (parent.getChildren().isEmpty()) {
      parent.setIsLeaf((byte) 1);
      // SkillTypeService skillTypeService = SpUtil.getBean(SkillTypeService.class);
      skillTypeService.updateById(parent);
    }
    typeMap.remove(skillTypeId);
    if (skillType.getChildren() != null && skillType.getChildren().size() > 0) {  //如果有子节点
      ArrayList<Long> ids = new ArrayList<>();
      delNodeAndChildNode(skillType, ids);
      skillTypeService.removeByIds(ids);
    } else {
      skillTypeService.removeById(skillTypeId);
    } 
  }

  public static void delNodeByIdList(Integer treeType, List<Long> skillTypeIdList) {
    Map<Long, SkillType> typeMap = typeAndAllNodeMap.get(treeType);
    for (Long skillTypeId : skillTypeIdList) {
      SkillType skillType = typeMap.get(skillTypeId);
      SkillType parent = typeMap.get(skillType.getParent());
      parent.removeChild(skillType);//获取parent,再从Parent中删除
      if (parent.getChildren().isEmpty()) {
        parent.setIsLeaf((byte) 1);
        // SkillTypeService skillTypeService = SpUtil.getBean(SkillTypeService.class);
        skillTypeService.updateById(parent);
      }
      typeMap.remove(skillTypeId);
    }
    // typeMap.remove(skillTypeId);
    skillTypeService.removeByIds(skillTypeIdList);
  }
}
