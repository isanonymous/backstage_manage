package cn.z.entity;

import cn.z.config.anno.JoinOn;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName(value = "wz",resultMap = "wenZhang")
@AllArgsConstructor
@NoArgsConstructor
public class WenZhang implements Serializable{
  private static final long serialVersionUID = -4702822851133416205L;
  @TableId
                      // @JsonSerialize(using=ToStringSerializer.class)  //将Long类型字段统一转成String类型
                      // @JsonDeserialize
  private Long wzId;
  private String title;
  private String content;
  // @DateTimeFormat(pattern="yyyy-MM-dd")  //把字符串转成该属性时使用
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")  //把该属性转成字符串时使用
  private Date lateUpdDate;

  @TableField(el="skillType.skillTypeId, jdbcType=OTHER")
  @JoinOn(tbl = "skill_type",onCol = "skill_type_id")
  private SkillType skillType;

  @Override
  public String toString() {
    return "WenZhang{" +
            "wzId=" + wzId +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", lateUpdDate=" + lateUpdDate +
            ", skillType=" + skillType +
            '}';
  }

  public Date getLateUpdDate() {
    return lateUpdDate;
  }

  public void setLateUpdDate(Date lateUpdDate) {
    this.lateUpdDate = lateUpdDate;
  }

  public Long getWzId() {
    return wzId;
  }

  public void setWzId(Long wzId) {
    this.wzId = wzId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public SkillType getSkillType() {
    return skillType;
  }

  public void setSkillType(SkillType skillType) {
    this.skillType = skillType;
  }
}
