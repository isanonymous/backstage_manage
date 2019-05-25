package cn.z.mapper;

import cn.z.entity.TestDeptBean;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import cn.z.config.anno.FromTbl;
import cn.z.config.anno.Ljoin;

import java.util.List;
import java.util.Map;

public interface TestDeptMapper_success extends BaseMapper<TestDeptBean> {
  @FromTbl("dept.name dname")
  @Ljoin( value = "shengId,sheng.sheng_name ",order = 1)
  List<TestDeptBean> selectDept3(IPage<TestDeptBean> page, @Param(Constants.WRAPPER) Wrapper<TestDeptBean> queryWrapper);

  List<TestDeptBean> selectDept3();

  // @Ljoin("233")
  // @Ljoin("2334")
  // @Rjoin("555")
  List<Map> selectEmp();
  @ResultType(Map.class)
  List<Map> testSelect();
}

