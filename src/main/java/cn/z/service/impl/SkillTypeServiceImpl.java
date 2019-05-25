package cn.z.service.impl;

import cn.z.entity.SkillType;
import cn.z.mapper.SkillTypeMapper;
import cn.z.service.SkillTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

@Service
public class SkillTypeServiceImpl extends ServiceImpl<SkillTypeMapper,SkillType> implements SkillTypeService {
  @Transactional
  @Override
  public boolean save(SkillType entity) {
    return super.save(entity);
  }

  @Transactional
  @Override
  public boolean updateById(SkillType entity) {
    return super.updateById(entity);
  }

  @Transactional
  @Override
  public boolean removeByIds(Collection<? extends Serializable> idList) {
    return super.removeByIds(idList);
  }

  @Transactional
  @Override
  public boolean removeById(Serializable id) {
    return super.removeById(id);
  }
}
