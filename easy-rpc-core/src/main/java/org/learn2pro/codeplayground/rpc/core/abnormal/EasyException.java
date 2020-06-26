package org.learn2pro.codeplayground.rpc.core.abnormal;

/**
 * the base easy rpc exception
 */
public class EasyException extends RuntimeException {

  public EasyException(String message) {
    super(message);
  }

  public EasyException(String msg, Throwable parent) {
    super(msg, parent);
  }
}
