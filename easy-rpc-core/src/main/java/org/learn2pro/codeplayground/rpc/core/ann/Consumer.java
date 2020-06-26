package org.learn2pro.codeplayground.rpc.core.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.learn2pro.codeplayground.rpc.core.enumerate.ProviderType;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Consumer {

  /**
   * the consume target
   *
   * @return target unique name
   */
  String value() default "";

  /**
   * provider in local or remote
   *
   * @return
   */
  ProviderType typo() default ProviderType.LOCAL;


}
