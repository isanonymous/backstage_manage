package cn.z.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SkillType implements Serializable{
  private static final long serialVersionUID = 6672849819988296452L;
  
  @TableId(type = IdType.ID_WORKER)  //只有当插入对象ID 为空，才自动填充。
                      // @JsonSerialize(using=ToStringSerializer.class)  //将Long类型字段统一转成String类型
  private Long skillTypeId;
  private String skillTypeName;
  private Long parent;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @TableField(exist=false)
  private List<SkillType> children;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @TableField(exist=false)
  private List<String> fullParent;
  
                      // private Byte level;
  private Byte isLeaf;
  private Integer type;

  public boolean addChild(SkillType st) {
    return children.add(st);
  }
  public boolean removeChild(SkillType st) {
    return children.remove(st);
  }
}
