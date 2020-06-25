package org.learn2pro.codeplayground.rpc.model;

import java.lang.reflect.Method;

/**
 * name:org.learn2pro.codeplayground.rpc.model.RpcRequest author:tderong date:2020/6/25
 */
public class RpcRequest implements RpcSerModel {

  /**
   * the rpc unique id
   */
  private String id;
  /**
   * the class for invocation
   */
  private Class<?> klazz;
  /**
   * the method to call
   */
  private Method method;
  /**
   * arg classes
   */
  private Class<?>[] argKlazz;
  /**
   * arg instance
   */
  private Object[] args;

  public RpcRequest() {
  }

  public RpcRequest(String id, Class<?> klazz, Method method, Class<?>[] argKlazz, Object[] args) {
    this.id = id;
    this.klazz = klazz;
    this.method = method;
    this.argKlazz = argKlazz;
    this.args = args;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Class<?> getKlazz() {
    return klazz;
  }

  public void setKlazz(Class<?> klazz) {
    this.klazz = klazz;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public Class<?>[] getArgKlazz() {
    return argKlazz;
  }

  public void setArgKlazz(Class<?>[] argKlazz) {
    this.argKlazz = argKlazz;
  }

  public Object[] getArgs() {
    return args;
  }

  public void setArgs(Object[] args) {
    this.args = args;
  }
}
