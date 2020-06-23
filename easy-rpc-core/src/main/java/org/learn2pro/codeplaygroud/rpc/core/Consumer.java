package org.learn2pro.codeplaygroud.rpc.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Consumer {
    /**
     * the consume target
     * @return target unique name
     */
    String target() default "";
}
