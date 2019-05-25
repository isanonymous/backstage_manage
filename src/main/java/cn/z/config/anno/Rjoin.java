package cn.z.config.anno;

import cn.z.config.JoinType;
import cn.z.config.anno.slave.ForRjoin;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ForRjoin.class)

@JoinAndSelect(joinType = JoinType.R, freeJoinType = "")
public @interface Rjoin {
  // JoinType joinType() default JoinType.L;

  @AliasFor(annotation = JoinAndSelect.class, attribute ="value" )
  String value() default "";

  String tblName() default "";

  int order() default 1;
}
