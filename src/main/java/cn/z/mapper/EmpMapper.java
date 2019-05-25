package cn.z.mapper;

import cn.z.config.anno.Ijoin;
import cn.z.config.anno.Ljoin;
import cn.z.config.anno.Rjoin;
import cn.z.entity.Emp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface EmpMapper extends BaseMapper<Emp>{
  // @JoinAndSelect(selectCol={"id dId","name dName","addr"},joinType = JoinType.I)
  @Ljoin(value = "id dId, name dName, addr",order = 1)
  @Rjoin(value ="*",order=2)
  @Ijoin(value ="*",order=3)
  @Ljoin(value ="*",order=4)
  List<Emp> selectDept3();
}
