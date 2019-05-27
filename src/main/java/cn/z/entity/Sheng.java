package cn.z.entity;

import cn.z.config.anno.JoinOn;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Sheng {
  @TableId("shengId")
  String shengId;
  String shengName;
  @JoinOn(tbl = "shi",onCol = "sId")
  Shi shi;
}
