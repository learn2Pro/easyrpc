package org.learn2pro.codeplayground.rpc.core;

public abstract class InvocationProxy {

  /**
   * the target bean name
   */
  private String targetName;

  /**
   * the target class
   */
  private Class<?> target;

  public InvocationProxy(String targetName, Class<?> target) {
    this.targetName = targetName;
    this.target = target;
  }

  public InvocationProxy() {
  }

  public String getTargetName() {
    return targetName;
  }

  public void setTargetName(String targetName) {
    this.targetName = targetName;
  }

  public Class<?> getTarget() {
    return target;
  }

  public void setTarget(Class<?> target) {
    this.target = target;
  }
}
