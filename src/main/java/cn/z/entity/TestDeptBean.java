package cn.z.entity;

import cn.z.config.anno.JoinOn;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@TableName("dept")
public class TestDeptBean {
  private Integer id ;//     int(11)
  private String name;//    varchar(13)
  private String addr;//    varchar(22)
  // @JoinOn(tbl = "sheng",onCol = "shengId")
  // private Sheng sheng;
}
