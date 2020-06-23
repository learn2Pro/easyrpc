package org.learn2pro.codeplayground.rpc.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

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
     * register consumer for class
     *
     * @param name  the instance name
     * @param inner the inner class
     * @return
     */
    public Object createProxy(String name, Class<?> inner) {
        String targetName = StringUtils.isEmpty(name) ? StringUtils.uncapitalize(inner.getSimpleName()) : name;
        this.app.containsBean(targetName);
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{inner}, new LocalInvocationWrapper(targetName));
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> target = bean.getClass();
        for (Field field : target.getDeclaredFields()) {
            //inject consumer
            if (field.getAnnotation(Consumer.class) != null && field.getType().isInterface()) {
                String name = field.getAnnotation(Consumer.class).value();
                Object instance = this.createProxy(name, field.getType());
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
}
