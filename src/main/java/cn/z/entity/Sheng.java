package cn.z.entity;

import cn.z.config.anno.JoinOn;

public class Sheng {
  String shengName;
  @JoinOn(tbl = "shi",onCol = "sId")
  Shi shi;
}
