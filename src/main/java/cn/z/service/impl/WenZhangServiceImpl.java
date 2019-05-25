package cn.z.service.impl;

import cn.z.entity.WenZhang;
import cn.z.mapper.WenZhangMapper;
import cn.z.service.WenZhangService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@CacheConfig(cacheNames = "wz"/*, cacheManager = "jsonCacheManager"*/)
@Service
public class WenZhangServiceImpl extends ServiceImpl<WenZhangMapper,WenZhang> implements WenZhangService {
  @Cacheable()
  @Override
  public WenZhang getById(Serializable id) {
    return super.getById(id);
  }

  @Cacheable(key = "#page.current+' & '+#page.size+' & '+(#queryWrapper!=null?#queryWrapper.getCustomSqlSegment()+#queryWrapper.getParamNameValuePairs().values():'null')")
  @Override
  public IPage<WenZhang> page(IPage<WenZhang> page, Wrapper<WenZhang> queryWrapper) {
    return super.page(page, queryWrapper);
  }

  @CachePut
  @Transactional
  @Override
  public boolean updateById(WenZhang entity) {
    return super.updateById(entity);
  }

  @CacheEvict
  @Transactional
  @Override
  public boolean removeById(Serializable id) {
    return super.removeById(id);
  }

  @Override
  public WenZhang getWzAndSkillTypeByWzId(@Param(Constants.WRAPPER) Wrapper<WenZhang> qw) {
    return baseMapper.getWzAndSkillTypeByWzId(qw);
  }

  public IPage<WenZhang> selectListPage(IPage<WenZhang> page, @Param(Constants.WRAPPER) Wrapper<WenZhang> qw) {
    return baseMapper.selectListPage(page,qw);
  }
}
