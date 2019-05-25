package cn.z.mapper;

import cn.z.config.anno.FromTbl;
import cn.z.config.anno.Ijoin;
import cn.z.config.anno.JoinAndSelect;
import cn.z.config.anno.Ljoin;
import cn.z.entity.Dept;
import cn.z.entity.Emp;
import cn.z.entity.WenZhang;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import java.util.List;

public interface WenZhangMapper extends BaseMapper<WenZhang>{
  Long ins(WenZhang wz);
  WenZhang selById(Long wz_id);
  List<WenZhang> list();
  Long upd(WenZhang wz);
  Long delPlural(Long... ids);

  Long del(Long wz_id);
  List<Emp> selectDept3();

  // @Override
  // @Bean2Col(field="skillType")
  // @Insert({"INSERT INTO wz(title,content,late_upd_date,skill_type) " +
  //         "VALUES(#{bean.title},}#{bean.content},#{bean.lateUpdDate},#{bean.skillType.skillTypeId})"})
  // int insert(WenZhang entity);

  @FromTbl("wz_id,title,content,late_upd_date")
  @Ljoin(value = "skill_type_id,skill_type_name,parent")  //,tblName = "skill_type")
  @ResultMap("wenZhang")
  WenZhang getWzAndSkillTypeByWzId(@Param(Constants.WRAPPER) Wrapper<WenZhang> queryWrapper);

  @FromTbl("wz_id,title,content,late_upd_date")
  @Ljoin(value = "skill_type_id,skill_type_name,parent")  //,tblName = "skill_type")
  // 等价于楼上// @JoinAndSelect(freeJoinType = "left join", value = "skill_type_id,skill_type_name,parent")
  @ResultMap("wenZhang")
  IPage<WenZhang> selectListPage(IPage<WenZhang> page,@Param(Constants.WRAPPER)  Wrapper<WenZhang> queryWrapper);

  @ResultMap("wenZhang")
  IPage<WenZhang> selectListPage22(IPage<WenZhang> page,@Param(Constants.WRAPPER)  Wrapper<WenZhang> queryWrapper);
}
