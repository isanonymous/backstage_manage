package cn.z.mapper;

import cn.z.config.anno.FromTbl;
import cn.z.config.anno.Ljoin;
import cn.z.config.anno.Rjoin;
import cn.z.entity.Dept;
import cn.z.entity.Emp;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;

import java.util.List;
import java.util.Map;

public interface DeptMapper extends BaseMapper<Dept>{
  @FromTbl("dept.name dname")
  @Ljoin( value = "shengId,sheng.sheng_name ",order = 1)
  List<Dept> selectDept3(IPage<Dept> page, @Param(Constants.WRAPPER) Wrapper<Dept> queryWrapper);

  List<Dept> selectDept3();

  // @Ljoin("233")
  // @Ljoin("2334")
  // @Rjoin("555")
  List<Map> selectEmp();
  @ResultType(Map.class)
  List<Map> testSelect();
}
