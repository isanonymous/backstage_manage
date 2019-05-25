package cn.z.entity;

import cn.z.config.anno.JoinOn;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Emp {
  private Integer id   ;//     int(11)
  private String name  ;//    varchar(13)
  private Integer age  ;//     int(3)
  private Date birthday;//  datetime
  
  //join哪张表, 查询这张表的哪些字段(会自动在前面加`d.`), 连接字段
  @JoinOn( tbl="dept", onCol="id")
  private Dept dept    ;//  int(11)

}
