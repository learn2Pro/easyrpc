package org.learn2pro.codeplayground.rpc.model;

/**
 * name:org.learn2pro.codeplayground.rpc.model.RpcRequest author:tderong date:2020/6/25
 */
public class RpcRequest implements RpcSerModel {

  /**
   * the rpc unique id
   */
  private String sessionId;
  /**
   * the service id
   */
  private String serviceId;
  /**
   * the class for invocation
   */
  private Class<?> klass;
  /**
   * the method to call
   */
  private String method;
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

  public RpcRequest(String sessionId, String serviceId, Class<?> klass, String method,
      Class<?>[] argKlazz, Object[] args) {
    this.sessionId = sessionId;
    this.serviceId = serviceId;
    this.klass = klass;
    this.method = method;
    this.argKlazz = argKlazz;
    this.args = args;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getServiceId() {
    return serviceId;
  }

  public void setServiceId(String serviceId) {
    this.serviceId = serviceId;
  }

  public Class<?> getKlass() {
    return klass;
  }

  public void setKlass(Class<?> klass) {
    this.klass = klass;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
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
