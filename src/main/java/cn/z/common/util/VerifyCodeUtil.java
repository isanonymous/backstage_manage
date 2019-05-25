package cn.z.common.util;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class VerifyCodeUtil {
  // private static ThreadLocalRandom random= ThreadLocalRandom.current();
  private static Random random= new Random();

  public static void outputImg(HttpServletResponse response) throws IOException {
    response.setContentType("image/jpeg");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    ServletOutputStream outputStream = response.getOutputStream();
    outputImg(outputStream);
    outputStream.close();
  }
  
  public static void outputImg(OutputStream output) throws IOException {
    int w=80, h=30;
    //1.生成矩形
    BufferedImage image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);

    Graphics g = image.getGraphics();
    //2.画矩形边框
    g.setColor(new Color(200,200,200));
    g.fillRect(0,0,w,h);

    //3.画线条
    for (int i = 0; i < h; i++) {
      // Random random = new Random();
      int x = random.nextInt(w);
      int y = random.nextInt(h);
      int xl = random.nextInt(x+10);
      int yl = random.nextInt(y+10);
      g.setColor(getColor());
      g.drawLine(x, y, x + xl, y + yl);
    }

    //4.随机数字
    int fontSize=24;
    g.setFont(new Font("Algerian", Font.ITALIC,fontSize));
    g.setColor(Color.darkGray);
    String checkNum = getNum();//"2525"

    // 添加噪点

    float yawpRate = 0.05f;// 噪声率

    int area = (int) (yawpRate * w * h);

    for (int i = 0; i < area; i++) {

      int x = random.nextInt(w);

      int y = random.nextInt(h);

      int rgb = getColor().getRGB();

      image.setRGB(x, y, rgb);

    }

    // StringBuffer sb = new StringBuffer();
    // for(int i=0;i<checkNum.length();i++){
    //   sb.append(checkNum.charAt(i)+" ");//"2 5 2 5"
    // }
    //文字的左下角移动的距离
    g.drawString(checkNum,8,24);

    // session.setAttribute("CHECKNUM",checkNum);//2525
    image.flush();
    g.dispose();
    ImageIO.write(image,"jpeg",output);  //需要调用者关闭输出流
  }

  private static Color getColor(){
    // Random random = new Random();
    int r = random.nextInt(256);//0-255
    int g = random.nextInt(256);
    int b = random.nextInt(256);
    return new Color(r,g,b);
  }
  private static String getNum(){
    String str = "";
    // Random random = new Random();
    for(int i=0;i<4;i++){
      str += random.nextInt(10);//0-9
    }
    return str;
  }
}
