package cn.z.mapper;

import cn.z.config.anno.FromTbl;
import cn.z.config.anno.Ijoin;
import cn.z.config.anno.JoinAndSelect;
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
  
  @FromTbl("emp.name, emp.dept ed")
  @JoinAndSelect(freeJoinType = "inner join",value = "dept.name dn, dept.sheng")
  @JoinAndSelect(order = 2,freeJoinType = "inner join",value = "sheng_name")
  //select emp.name, emp.dept ed,dept.name dn, dept.sheng,sheng_name 
    // from emp 
    // inner join Dept on Emp.dept=Dept.id 
    // inner join sheng on Dept.sheng=sheng.shengId 
  List<Emp> empDeptSheng();
}
