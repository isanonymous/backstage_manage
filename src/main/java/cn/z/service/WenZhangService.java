package cn.z.service;

import cn.z.entity.Dept;
import cn.z.entity.WenZhang;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

public interface WenZhangService extends IService<WenZhang> {
  WenZhang getWzAndSkillTypeByWzId(@Param(Constants.WRAPPER) Wrapper<WenZhang> queryWrapper);

  public IPage<WenZhang> selectListPage(IPage<WenZhang> page, @Param(Constants.WRAPPER) Wrapper<WenZhang> qw);
}
