package cn.z.entity;

import cn.z.config.anno.JoinOn;

public class Shi {
  String name;
  @JoinOn(tbl = "qu",onCol = "quId")
  Qu qu;
}
