package cn.z.config.filter;

import cn.z.common.simplify.RespResult;
import cn.z.common.util.CookieUtil;
import cn.z.common.util.JsonUtil;
import cn.z.common.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Order(10)
@WebFilter(urlPatterns = "/doLogin", filterName = "loginFilter")
// @WebFilter(urlPatterns = { "/*" }, filterName = "loginAuthFilter")
public class LoginFilter implements Filter {
  // private static Logger logger = LoggerFactory.getLogger(LoginAuthFilter.class);
  @Value("${login_proof_cookie_key}")
  private String LOGIN_PROOF;
  @Value("#{${login_proof_cookie_save_time}}")
  private int LOGIN_PROOF_SAVE_TIME;
  @Value("${auto_login_key}")
  private String REMEMBER;
  
  @Autowired
  StringRedisTemplate stringRedisTemplate;

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;
      Cookie[] cookies = req.getCookies();
      if (cookies != null && cookies.length > 0) {
        for (Cookie cookie : cookies) {
          String cookieValue = cookie.getValue();
          String redisVal = stringRedisTemplate.opsForValue().get(cookieValue);
          if (cookie.getName().equals(LOGIN_PROOF) && StrUtil.isNotEmpty(redisVal) && cookieValue.startsWith(REMEMBER)) {
            // String userId = UUID.randomUUID().toString().replace("-", "");
            // CookieUtil.setCookie(req, resp, LOGIN_PROOF, userId, LOGIN_PROOF_SAVE_TIME);  //3天
            // stringRedisTemplate.delete(cookie.getValue());
            // stringRedisTemplate.opsForValue().set(userId, LOGIN_PROOF, LOGIN_PROOF_SAVE_TIME, TimeUnit.SECONDS);

            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(JsonUtil.objectToJson(RespResult.build(1, "自动登陆成功", redisVal)));
            pw.flush();
            pw.close();
            return;
          }
        }
        // chain.doFilter(req, resp);
      }else {  //cookies == null
        chain.doFilter(req, resp);
      }
    
    // if ()
    chain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
  }
  
}
