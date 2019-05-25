package cn.z.ctrl.study.javaa;

import cn.z.State;
import cn.z.common.simplify.DBMap;
import cn.z.common.simplify.PageData;
import cn.z.common.simplify.RespResult;
import cn.z.common.util.JsonUtil;
import cn.z.entity.WenZhang;
import cn.z.mapper.WenZhangMapper;
import cn.z.entity.UserAddress;
import cn.z.service.OrderService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @RestController
// @RequestMapping("/api/study/java")
public class _FrameCtrl_old {
  @Autowired
  OrderService orderService;
  @Autowired
  WenZhangMapper wenZhangDao;
  

  /*@GetMapping(value="/order/{userId}")
  public List<UserAddress> order(@PathVariable String userId)throws Exception{
    List<UserAddress> userAddresses = orderService.initOrder(userId);
    return userAddresses;
  }*/
  
  @PostMapping(value="/frame")
  public RespResult create(@RequestBody WenZhang wz)throws Exception{
    // resp.setHeader("Access-Control-Allow-Origin","*");
    // resp.setHeader("Access-Control-Allow-Headers","token,Origin, X-Requested-With, Content-Type, Accept,mid,X-Token");
    // resp.setHeader("Access-Control-Request-Method","GET, POST");
    
    System.out.println("-----create()----: ");
    Long ins = wenZhangDao.ins(wz);
    // HashMap<String, Object> hashMap = new HashMap<>();
    // hashMap.put("state", ins > 0? State.SUCCESS:State.FAIL);
    return RespResult.buildByUpdateSqlResult(ins);
  }

  @PutMapping(value="/frame/{wz_id}")
  public RespResult edit(@RequestBody WenZhang wz)throws Exception{
    Long upd = wenZhangDao.upd(wz);
    System.out.println(upd);
    // HashMap<String, Object> hashMap = new HashMap<>();
    // hashMap.put("state", upd > 0? State.SUCCESS:State.FAIL);
    return RespResult.buildByUpdateSqlResult(upd);
  }

  @SuppressWarnings("unchecked")
  @GetMapping(value="/frameList")
  // public PageData list(PageData pageInfo, @RequestBody(required=false)DBMap params)throws Exception{
  public Page list(Page<WenZhang> page, @RequestBody(required=false)DBMap params)throws Exception{
    // System.out.println("-----create()----: "+req.getQueryString());
    // PageData pageInfoMap = JsonUtil.jsonToObj(pageInfo, PageData.class);
    // int pageNum = pageInfo.getPageNum();  // 页号
    // int pageSize = pageInfo.getPageSize();  // 页大小
    // System.out.println(pageNum+"---"+pageSize);
                        //设置分页 （ps：只对下面的第一个select有效）
                        // PageHelper.startPage(pageNum, pageSize);
    // Page<WenZhang> page = new Page(pageNum, pageSize);
    IPage<WenZhang> iPage = wenZhangDao.selectPage(page, new QueryWrapper<WenZhang>());
    // List<WenZhang> list = iPage.getRecords();
    // System.out.println(list);
                        //、取分页后结果
                        // PageInfo pageResult = new PageInfo(list);
                        // int total = (int)pageResult.getTotal();  // 总记录数
    long total = iPage.getTotal();
    return page;
  }
  
  @GetMapping(value="/frame/{wz_id}")
  public WenZhang getById(@PathVariable Long wz_id)throws Exception{
    System.out.println(wz_id);
    return wenZhangDao.selById(wz_id);
  }

  @DeleteMapping(value="/frame/{wz_id}")
  public RespResult wz(@PathVariable Long wz_id)throws Exception{
    // System.out.println(Arrays.toString(wz_id));
    Long del = wenZhangDao.del(wz_id);
    return RespResult.buildByUpdateSqlResult(del);
  }

  /** 删除多个*/
  @DeleteMapping(value="/frame")
  public RespResult delPlural(@RequestBody Long... wz_id)throws Exception{
    System.out.println(Arrays.toString(wz_id));
    Long del = wenZhangDao.delPlural(wz_id);
    return RespResult.buildByUpdateSqlResult(del);
  }
}
