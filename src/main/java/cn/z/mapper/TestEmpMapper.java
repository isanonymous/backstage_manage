package cn.z.mapper;

import cn.z.common.TestEmpBean;
import cn.z.config.anno.FromTbl;
import cn.z.config.anno.Ljoin;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;

import java.util.List;
import java.util.Map;

public interface TestEmpMapper extends BaseMapper<TestEmpBean> {
  @FromTbl("emp.name ename")
  @Ljoin( value = "dept.name dn, dept.addr",order = 1)
  List<TestEmpBean> selectDept3(IPage<TestEmpBean> page, @Param(Constants.WRAPPER) Wrapper<TestEmpBean> queryWrapper);

  @FromTbl("emp.name ename")
  @Ljoin( value = "dept.name dn, dept.addr",order = 1)
  List<TestEmpBean> selectDept3();

  // @Ljoin("233")
  // @Ljoin("2334")
  // @Rjoin("555")
  List<Map> selectEmp();
  @ResultType(Map.class)
  List<Map> testSelect();
}

