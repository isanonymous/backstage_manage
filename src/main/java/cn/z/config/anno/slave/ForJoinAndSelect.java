package cn.z.config.anno.slave;

import cn.z.config.anno.JoinAndSelect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ForJoin(ForJoinAndSelect.class)
public @interface ForJoinAndSelect {
  JoinAndSelect[] value(); 
}
