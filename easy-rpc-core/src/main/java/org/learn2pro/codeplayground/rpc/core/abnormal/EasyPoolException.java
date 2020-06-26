package org.learn2pro.codeplayground.rpc.core.abnormal;

/**
 * the base easy rpc exception
 */
public class EasyPoolException extends RuntimeException {

  public EasyPoolException(String message) {
    super(message);
  }

  public EasyPoolException(String msg, Throwable parent) {
    super(msg, parent);
  }
}
