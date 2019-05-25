package cn.z.mapper;

import cn.z.entity.SkillType;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.type.LongTypeHandler;

import java.util.List;

public interface SkillTypeMapper extends BaseMapper<SkillType> {
  // @ResultMap("cn.z.mapper.SkillTypeMapper.skillType")
  // @Results({
  //   @Result(column = "skill_type_id",javaType = Long.class,property = "skillTypeId",typeHandler = LongTypeHandler.class),
  //   @Result(column = "skill_type_name",javaType = String.class,property = "skillTypeName"),
  //   @Result(column = "parent",javaType = Long.class,property = "parent")
  // })
  // @Override
  // List<SkillType> selectList(Wrapper<SkillType> queryWrapper);
}
