package org.learn2pro.codeplayground.rpc.core.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Consumer {

  /**
   * the consume target
   *
   * @return target unique name
   */
  String value() default "";
}
