package org.learn2pro.codeplayground.rpc.core.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Provider {

  /**
   * the provide unique name
   *
   * @return name desc
   */
  String value() default "";
}
