package cn.z.ctrl.study.javaa;

import cn.z.State;
import cn.z.common.constant.TreeType;
import cn.z.common.simplify.DBMap;
import cn.z.common.simplify.PageData;
import cn.z.common.simplify.RespResult;
import cn.z.common.util.ArrUtil;
import cn.z.common.util.JsonUtil;
import cn.z.common.util.StrUtil;
import cn.z.config.TreeCache;
import cn.z.entity.SkillType;
import cn.z.entity.WenZhang;
import cn.z.entity.UserAddress;
import cn.z.service.OrderService;
import cn.z.service.WenZhangService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
// import com.github.pagehelper.PageHelper;
// import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/study/java")
public class FrameCtrl {
  @Autowired
  OrderService orderService;
  @Autowired
  WenZhangService wenZhangService;

  @PostMapping(value="/frame")
  public RespResult create(@RequestBody WenZhang wz)throws Exception{
    // Long ins = wenZhangDao.ins(wz);
    wz.setLateUpdDate(new Date());
    boolean ins = wenZhangService.save(wz);
    return RespResult.buildByUpdateSqlResult(ins);
  }

  @PutMapping(value="/frame/{wz_id}")
  public RespResult edit(@RequestBody WenZhang wz)throws Exception{
    // Long upd = wenZhangService.upd(wz);
    // System.out.println(upd);
    wz.setLateUpdDate(new Date());
    return RespResult.buildByUpdateSqlResult(wenZhangService.updateById(wz));
  }

  @SuppressWarnings("unchecked")
  @GetMapping(value="/frameList")
  public IPage list(Page<WenZhang> page, HttpServletRequest req)throws Exception{
    DBMap dbMap = new DBMap(req);
    QueryWrapper<WenZhang> qw;
    if (!dbMap.isEmpty()) {
      qw = new QueryWrapper<WenZhang>();
      // String temp;
      // if (StrUtil.isNotBlank(temp = dbMap.getStr("frameName")) ) {
      //   qw.like("title", temp);
      // }
      // String[] dates;
      // if (ArrUtil.isNotEmpty(dates = req.getParameterMap().get("lateUpdDate[]")) ) {
      //   qw.between("late_upd_date", dates[0], dates[1]);
      // }
      // if (StrUtil.isNotBlank(temp = dbMap.getStr("frameType")) ) {
      //   qw.eq("skill_type", temp);
      // }
      qw.like(dbMap.getStrIsNotEmpty("frameName"),"title", dbMap.getStr("frameName"));
      String[] dates = req.getParameterMap().get("lateUpdDate[]");
      if (ArrUtil.isNotEmpty(dates)) {
        qw.between("late_upd_date", dates[0], dates[1]);
      }
      qw.eq(dbMap.getStrIsNotEmpty("frameType"),"skill_type", dbMap.getStr("frameType"));
    } else {
      // qw = null;
      qw = new QueryWrapper<>();
    } 
    System.out.println(page.getCurrent()+" "+page.getSize()+" "+(qw!=null?qw.getCustomSqlSegment()+qw.getParamNameValuePairs().values():"null") );
    // IPage<WenZhang> iPage = wenZhangService.page(page, qw);
    IPage<WenZhang> iPage = wenZhangService.selectListPage(page, qw);
    // long total = iPage.getTotal();
    // System.out.println("\n        "+iPage.getRecords());
    return iPage;
  }

  @GetMapping(value="/frame/{wz_id}")
  public WenZhang getById(@PathVariable Long wz_id)throws Exception{
    System.out.println("\n----wz_id---: " + wz_id);
    // return wenZhangService.getById(wz_id);
    WenZhang wz = wenZhangService.getWzAndSkillTypeByWzId(new QueryWrapper<WenZhang>().eq("wz_id", wz_id));

    SkillType selectSkillType = wz.getSkillType();
    if (selectSkillType!=null) {
      SkillType skillType = TreeCache.typeAndAllNodeMap.get(TreeType.SKILL).get(selectSkillType.getSkillTypeId());
      selectSkillType.setFullParent(skillType.getFullParent());
    }
    return wz;
  }

  @DeleteMapping(value="/frame/{wz_id}")
  public RespResult wz(@PathVariable Long wz_id)throws Exception{
    // System.out.println(Arrays.toString(wz_id));
    return RespResult.buildByUpdateSqlResult(wenZhangService.removeById(wz_id));
  }

  /** 删除多个*/
  @DeleteMapping(value="/frame")
  public RespResult delPlural(@RequestBody List<Long> wz_id)throws Exception{
    // System.out.println(Arrays.toString(wz_id));
    // Long del = wenZhangService.delPlural(wz_id);
    boolean del = wenZhangService.removeByIds(wz_id);
    return RespResult.buildByUpdateSqlResult(del);
  }
}
