package cn.z.ctrl;

import cn.z.common.simplify.RespResult;
import cn.z.common.util.CookieUtil;
import cn.z.common.util.VerifyCodeUtils;
import cn.z.entity.Account;
import cn.z.service.AccountService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
@RestController
public class LoginCtrl {
  @Autowired
  StringRedisTemplate stringRedisTemplate;
  @Autowired
  AccountService accountService;
  @Value("${captcha_key}")
  private String CAPTCHA;
  @Value("${login_proof_cookie_key}")
  private String LOGIN_PROOF;
  @Value("#{${login_proof_cookie_save_time}}")
  private int LOGIN_PROOF_SAVE_TIME;
  @Value("${auto_login_key}")
  private String REMEMBER;
  @Value("${not_auto_login_key}")
  private String DISREMEMBER;
  
  @GetMapping(value="/authcode")
  public void authcode(HttpServletRequest request, HttpServletResponse response)throws Exception{
    String code = VerifyCodeUtils.generateVerifyCode(4);
    String userId = UUID.randomUUID().toString().replace("-", "");
    stringRedisTemplate.opsForValue().set("captcha:"+userId, code, 120, TimeUnit.SECONDS);

    // Cookie cookie = new Cookie("CAPTCHA", userId);
    // response.addCookie(cookie);
    CookieUtil.setCookie(request,response,CAPTCHA, userId, 120);  //2分钟

    response.setContentType("image/jpeg");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    ServletOutputStream outputStream = response.getOutputStream();
    VerifyCodeUtils.outputImage(100, 40, outputStream, code);
    if (outputStream != null) {
      outputStream.close();
    }
  }

  @GetMapping(value = "/doLogin",produces = "application/json;charset=utf-8")
  RespResult doLogin(Account account, @CookieValue(value="LOGIN_PROOF",required=false) String cookie, @CookieValue("CAPTCHA") String captchaKey
          , @RequestParam("remember") boolean remember, HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (captchaKey == null) {
      return RespResult.build(0, "非法登陆来源");
    }
    String code = stringRedisTemplate.opsForValue().get("captcha:"+captchaKey);
    if (code == null) {
      return RespResult.build(0, "验证码已过期");
    }
    if (!account.getCaptcha().equalsIgnoreCase(code)) {
      return RespResult.build(0, "错误的验证码");
      // authcode(request, response);
    }
    Account byId = accountService.getOne(new QueryWrapper<Account>()
            .eq("username",account.getUsername())
            .eq("password",account.getPassword()));
    if (byId != null) {
      String uuid = UUID.randomUUID().toString().replace("-", "");

      stringRedisTemplate.delete("captcha:" + captchaKey);
      String cookieValue = remember ? REMEMBER + uuid : DISREMEMBER + uuid;
      CookieUtil.setCookie(request,response,LOGIN_PROOF, cookieValue, remember?LOGIN_PROOF_SAVE_TIME:-1);  //3天
      stringRedisTemplate.opsForValue().set(cookieValue, account.getUsername(), LOGIN_PROOF_SAVE_TIME, TimeUnit.SECONDS);
      return RespResult.build(1, "登陆成功");
      
    } else {
      return RespResult.build(0, "用户名或密码错误");
    } 
  }

  @GetMapping(value = "/doLogout",produces = "application/json;charset=utf-8")
  RespResult doLogout(@CookieValue("LOGIN_PROOF") String cookie
          , HttpServletRequest request, HttpServletResponse response) throws Exception {
    String code = stringRedisTemplate.opsForValue().get(cookie);
    if (code != null) {
      stringRedisTemplate.delete(cookie);
      // stringRedisTemplate.delete(DISREMEMBER+cookie);
    }
    CookieUtil.setCookie(request,response,LOGIN_PROOF, "", 0);  //删除
    return RespResult.build(1, "退出成功");
  }


    // public void authcode(HttpServletResponse response)throws Exception{
  //   // VerifyCodeUtils.outputImage(100, 40, resp.getOutputStream(), "null");
  //
  //   // HttpSession session=request.getSession();
  //   int width = 62, height = 22;
  //   String[] ops={"+","-","*","/","="};
  //   BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  //   Graphics2D g = buffImg.createGraphics();
  //
  //   g.setColor(Color.WHITE);
  //   g.fillRect(0, 0, width, height);
  //
  //   g.setColor(Color.BLACK);
  //   g.drawRect(0, 0, width - 1, height - 1);
  //
  //   g.setColor(Color.GRAY);
  //   Random random = new Random();
  //   for (int i = 0; i < 20; i++) {  //mmyy 19/5/22 10:19:15
  //     int x1 = random.nextInt(width);
  //     int y1 = random.nextInt(height);
  //     int x2 = random.nextInt(10);
  //     int y2 = random.nextInt(10);
  //     g.drawLine(x1, y1, x1 + x2, y1 + y2);
  //   }
  //
  //   Font font = new Font("Times New Roman", Font.PLAIN, 18);
  //   g.setFont(font);
  //
  //   int num1=random.nextInt(10);
  //   String strRand1 = String.valueOf(num1);
  //   int red1 = random.nextInt(255);
  //   int green1 = random.nextInt(255);
  //   int blue1 = random.nextInt(255);
  //   g.setColor(new Color(red1, green1, blue1));
  //   g.drawString(strRand1, 13 *0 + 6, 16);
  //
  //   int op_num=random.nextInt(4);
  //   String strRand2 =(String)ops[op_num];
  //   int red2 = random.nextInt(255);
  //   int green2 = random.nextInt(255);
  //   int blue2 = random.nextInt(255);
  //   g.setColor(new Color(red2, green2, blue2));
  //   g.drawString(strRand2, 13 *1 + 6, 16);
  //
  //   int num2=(random.nextInt(9)+1);
  //   String strRand3 = String.valueOf(num2);
  //   int red3 = random.nextInt(255);
  //   int green3 = random.nextInt(255);
  //   int blue3 = random.nextInt(255);
  //   g.setColor(new Color(red3, green3, blue3));
  //   g.drawString(strRand3, 13 *2 + 6, 16);
  //
  //   String strRand4 =(String)ops[4] ;
  //   int red4 = random.nextInt(255);
  //   int green4 = random.nextInt(255);
  //   int blue4 = random.nextInt(255);
  //   g.setColor(new Color(red4, green4, blue4));
  //   g.drawString(strRand4, 13 *3 + 6, 16);
  //
  //   Integer randomCode=0;
  //   switch(op_num){
  //     case 0:
  //       randomCode = num1+num2;
  //       break;
  //     case 1:
  //       randomCode = num1-num2;
  //       break;
  //     case 2:
  //       randomCode = num1*num2;
  //       break;
  //     case 3:
  //       randomCode = num1/num2;
  //       break;
  //   }
  //   // session.setAttribute("randomCode", randomCode.toString());
  //
  //   buffImg.flush();
  //   g.dispose();
  //   response.setContentType("image/jpeg");
  //   response.setHeader("Pragma", "no-cache");
  //   response.setHeader("Cache-Control", "no-cache");
  //   response.setDateHeader("Expires", 0);
  //   ServletOutputStream outputStream = response.getOutputStream();
  //   ImageIO.write(buffImg, "jpeg", outputStream);
  //   outputStream.close();
  //
  // }
}
