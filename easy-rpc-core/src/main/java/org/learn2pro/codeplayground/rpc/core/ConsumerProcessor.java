package org.learn2pro.codeplayground.rpc.core;

import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Set;
import org.learn2pro.codeplayground.rpc.core.ann.Consumer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

/**
 * consumer post hook
 *
 * @author: Dell
 * @DATE: 2020/6/23
 */
public class ConsumerProcessor implements BeanPostProcessor, ApplicationContextAware {

  /**
   * the app instance
   */
  private ApplicationContext app;
  /**
   * remote rpc services
   */
  private Set<String> remoteServices = Sets.newHashSet();

  /**
   * register consumer for class
   *
   * @param targetName the instance name
   * @param inner      the inner class
   * @return
   */
  private Object createProxy(String targetName, Class<?> inner) {
    if (this.app.containsBean(targetName)) {
      return Proxy
          .newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{inner},
              new LocalInvocationWrapper(targetName));
    } else {
      return Proxy
          .newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{inner},
              new RemoteInvocationWrapper(targetName));
    }
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    Class<?> target = bean.getClass();
    for (Field field : target.getDeclaredFields()) {
      //inject consumer
      if (field.getAnnotation(Consumer.class) != null && field.getType().isInterface()) {
        String name = field.getAnnotation(Consumer.class).value();
        String targetName =
            StringUtils.isEmpty(name) ? StringUtils.uncapitalize(field.getType().getSimpleName())
                : name;
        if (!this.app.containsBean(targetName)) {
          remoteServices.add(targetName);
        }
        Object instance = this.createProxy(targetName, field.getType());
        field.setAccessible(true);
        ReflectionUtils.setField(field, bean, instance);
      }
    }
    return bean;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.app = applicationContext;
  }

  /**
   * get all remote services
   *
   * @return service set
   */
  public Set<String> getRemoteServices() {
    return remoteServices;
  }
}
