package cn.z.config.filter;

import cn.z.common.simplify.RespResult;
import cn.z.common.util.CookieUtil;
import cn.z.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class LoginInterceptor implements HandlerInterceptor {
  @Value("${login_proof_cookie_key}")
  private String LOGIN_PROOF;
  @Value("#{${login_proof_cookie_save_time}}")
  private int LOGIN_PROOF_SAVE_TIME;

  @Autowired
  StringRedisTemplate stringRedisTemplate;
  
  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
    Cookie[] cookies = req.getCookies();
    if (cookies != null && cookies.length > 0) {
      for (Cookie cookie : cookies) {
        String cookieValue = cookie.getValue();
        if (cookie.getName().equals(LOGIN_PROOF) && stringRedisTemplate.hasKey(cookieValue)) {
          String userId = UUID.randomUUID().toString().replace("-", "");
          CookieUtil.setCookie(req, resp, LOGIN_PROOF, userId, LOGIN_PROOF_SAVE_TIME);  //3天
          String username = stringRedisTemplate.opsForValue().get(cookieValue);
          stringRedisTemplate.delete(cookieValue);
          stringRedisTemplate.opsForValue().set(userId, LOGIN_PROOF, LOGIN_PROOF_SAVE_TIME, TimeUnit.SECONDS);

          resp.setContentType("application/json;charset=UTF-8");
          PrintWriter pw = resp.getWriter();
          pw.write(JsonUtil.objectToJson(RespResult.build(1, username.equals(LOGIN_PROOF)?"自动登陆成功":username)));
          pw.flush();
          pw.close();
          // chain.doFilter(req, resp);
          // return;
          // return RespResult.build(1, "登陆成功");
        }
      }
    } else {
      // chain.doFilter(req, resp);
    }
    // return false;
    return req.getParameter("doAutoLogin")==null;
  }
}
