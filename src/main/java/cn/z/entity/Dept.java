package cn.z.entity;

import cn.z.config.anno.JoinOn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Dept {
  private Integer id ;//     int(11)
  private String name;//    varchar(13)
  private String addr;//    varchar(22)
  @JoinOn(tbl = "sheng",onCol = "shengId")
  private Sheng sheng;
}
