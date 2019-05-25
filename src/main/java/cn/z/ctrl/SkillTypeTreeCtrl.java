package cn.z.ctrl;

import cn.z.common.constant.TreeType;
import cn.z.common.simplify.RespResult;
import cn.z.config.TreeCache;
import cn.z.entity.SkillType;
import cn.z.mapper.SkillTypeMapper;
import cn.z.service.SkillTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

@RestController
public class SkillTypeTreeCtrl {
  @Autowired
  SkillTypeService skillTypeService;
  
  @GetMapping(value="/skillTypeTreeList")
  public List loadSkillTypeTree(Integer treeType)throws Exception{
    // skillTypeMapper.insert(st);
    // List<SkillType> skillTypes = skillTypeMapper.selectList(null);
    if (TreeCache.typeAndAllNodeMap.get(treeType) == null) {
      List<SkillType> skillTypes = skillTypeService.list(new QueryWrapper<SkillType>().eq("type",treeType));
      return TreeCache.getEltreeData(skillTypes, treeType);
    }
    
    // return parseListToEltree(skillTypes, treeType);
    return TreeCache.getEltreeData(treeType);
  }

  @PostMapping("/skillType")
  RespResult addSkillType(@RequestBody SkillType st, HttpServletRequest req) {
    boolean insert = skillTypeService.save(st);
    TreeCache.addOrUpdNode(TreeType.SKILL, st);
    return RespResult.buildByUpdateSqlResult(insert, TreeCache.getEltreeData(TreeType.SKILL));
  }

  @PutMapping("/skillType/rename")
  RespResult editSkillType(@RequestBody SkillType st) {
    boolean insert = skillTypeService.updateById(st);
    st = skillTypeService.getById(st.getSkillTypeId());
    TreeCache.addOrUpdNode(TreeType.SKILL, st);
    return RespResult.buildByUpdateSqlResult(insert, TreeCache.getEltreeData(TreeType.SKILL));
  }
  
  @DeleteMapping("/skillType/{skillTypeId}")
  RespResult delSkillType(@PathVariable Long skillTypeId) {
    boolean updResult = skillTypeService.removeById(skillTypeId);
    TreeCache.delNode(TreeType.SKILL, skillTypeId);
    return RespResult.buildByUpdateSqlResult(updResult, TreeCache.getEltreeData(TreeType.SKILL));
  }

  /** 删除多个*/
  @DeleteMapping(value="/frame")
  public RespResult delPlural(@RequestBody List<Long> skillTypeIdList)throws Exception{
    boolean del = skillTypeService.removeByIds(skillTypeIdList);
    TreeCache.delNodeByIdList(TreeType.SKILL, skillTypeIdList);
    return RespResult.buildByUpdateSqlResult(del, TreeCache.getEltreeData(TreeType.SKILL));
  }

  
  
  @Deprecated
  /**
   * @see TreeCache#getEltreeData(List , int )
   */
  private List<SkillType> __parseListToEltree(List<SkillType> li, int treeType) {
    Comparator<Long> comparator =(o1, o2) -> (int)(o1-o2); // Comparator.comparingLong(  value -> value  );
    TreeMap<Long, SkillType> leafTreeMap=new TreeMap(comparator);  //所有的子节点
    TreeMap<Long, SkillType> parentTreeMap=new TreeMap(comparator);  //所有的父节点
    TreeMap<Long, SkillType> cacheMap=new TreeMap(comparator);  //所有的节点
    ArrayList<SkillType> resultList = new ArrayList<>();
    
    for (SkillType object : li) {  //把数据存到map中,并按isLeaf区分
      cacheMap.put(object.getSkillTypeId(), object);
              
      if (object.getIsLeaf() == 1) {
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

  @Deprecated
  /**
   * @see TreeCache#addFullParent(SkillType)
   */
  private void addFullParent(SkillType parent) {
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

  private void getFullParentId(TreeMap<Long, SkillType> cacheMap, Set<Long> keySet, SkillType object) {
    // System.out.println("\n----node111name---: " + object.getSkillTypeName());
    SkillType parent = null;
    for (Long pId : keySet) {
      if (pId.longValue() == object.getParent()) {  //都是Long,不能直接==
                                    // System.out.println("\n----node222name---: " + object.getSkillTypeName());
        parent = cacheMap.get(pId);
        if (object.getFullParent() == null) {
          object.setFullParent(new LinkedList<>());
        }
        // lAddPId(object, pId);
        String pIdStr = pId.toString();
        object.getFullParent().add(0, pIdStr);  //给自己添加fullpid
        
        if (object.getChildren() != null) {
          for (SkillType child : object.getChildren()) {
            List<String> fullParent = child.getFullParent();
            if (fullParent != null && !fullParent.contains(pIdStr)) {
              fullParent.add(0, pIdStr);
            }
          }
        }
        
        object=parent;
        if (object.getParent()!=null && object.getParent()!=0) {
          getFullParentId(cacheMap, keySet, object);
        }
      }
    }
  }

  private void lAddPId(SkillType object, Long pId) {
    if (object.getFullParent() == null) {
      object.setFullParent(new LinkedList<>());
    }
    if (!object.getFullParent().contains(pId.toString())) {
      object.getFullParent().add(0, pId.toString());
    }
    if (object.getChildren() != null) {
      for (SkillType child : object.getChildren()) {
        // lAddPId(child, object.getSkillTypeId());
        lAddPId(child, object.getSkillTypeId());
      }
    }
  }

  // private List<SkillType> parseListToEltree(List<SkillType> li) {
  //   // ArrayList<SkillType> resultList = new ArrayList<>();
  //   List<LinkedHashMap<Long, SkillType>> classifyNodeByLevel = new ArrayList<>(3);
  //   classifyNodeByLevel.add(new LinkedHashMap<>());
  //   classifyNodeByLevel.add(new LinkedHashMap<>());
  //   classifyNodeByLevel.add(new LinkedHashMap<>());
  //  
  //   // Map<Long, SkillType> map = new TreeMap<>();
  //   for (SkillType object : li) {
  //     for (int i = 0; i < classifyNodeByLevel.size(); i++) {
  //       // System.out.println(object.getSkillTypeName());
  //       if (object.getLevel() == i) {  //根节点
  //         classifyNodeByLevel.get(i).put(object.getSkillTypeId(), object);
  //         // object.setChildren(new ArrayList<>());
  //         // resultList.add(object);
  //       }
  //     }
  //   }
  //   for (int i = 1; i < classifyNodeByLevel.size(); i++) {
  //     LinkedHashMap<Long, SkillType> mapI = classifyNodeByLevel.get(i);
  //     LinkedHashMap<Long, SkillType> mapI_1 = classifyNodeByLevel.get(i-1);
  //     for (SkillType skillType : mapI.values()) {
  //       SkillType temp;
  //       if ((temp = mapI_1.get(skillType.getParent())) != null) {
  //         if (temp.getChildren() == null) {  //保证父节点有children
  //           temp.setChildren(new ArrayList<>());
  //         }
  //         temp.add(skillType);
  //       }
  //       // if (i != classifyNodeByLevel.size() && classifyNodeByLevel.get(i+1).)
  //       if (skillType.getIsLeaf() == 1) {
  //         temp=skillType;
  //         List<String> fullParent = new ArrayList<>();
  //         while (temp != null && temp.getParent() != null && temp.getParent() != 0) {
  //           // sb.insert(0, temp.getParent());
  //           fullParent.add(temp.getParent()+"");
  //           temp=classifyNodeByLevel.get(temp.getLevel()-1).get(temp.getParent());
  //         }
  //         skillType.setFullParent(fullParent);
  //       }
  //     }
  //   }
  //  
  //   // out:for (SkillType object : li) {
  //     // for (int i = 0; i < classifyNodeByLevel.size(); i++) {
  //     //   if (object.getLevel() != 0) {
  //     //     LinkedHashMap<Long, SkillType> linkedHashMap = classifyNodeByLevel.get(object.getLevel() - 1);
  //     //     for (Long id : linkedHashMap.keySet()) {
  //     //       if (id.longValue() == object.getParent()) {
  //     //         SkillType skillType = linkedHashMap.get(id);
  //     //         if (skillType.getChildren() == null) {  //保证父节点有children
  //     //           skillType.setChildren(new ArrayList<>());
  //     //         }
  //     //         skillType.add(object);
  //     //         continue out;
  //     //       }
  //     //     }
  //     //   }
  //     // }
  //   // }
  //   return new ArrayList<>(classifyNodeByLevel.get(0).values());
  //    
  //   //   else {
  //   //     if (map.containsKey(object.getParent())) {  //map中有它的parent
  //   //       SkillType skillType = map.get(object.getParent());  //获取父节点
  //   //       if (skillType.getChildren() == null) {  //保证父节点有children
  //   //         skillType.setChildren(new ArrayList<>());
  //   //       }
  //   //       skillType.add(object);
  //   //     }
  //   //     // delList.add(object);
  //   //   }
  //   // }
  //   // return resultList;
  // }
}
