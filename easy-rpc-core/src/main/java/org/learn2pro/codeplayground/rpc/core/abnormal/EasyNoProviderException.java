package org.learn2pro.codeplayground.rpc.core.abnormal;

/**
 * the base easy rpc exception
 */
public class EasyNoProviderException extends RuntimeException {

  public EasyNoProviderException(String message) {
    super(message);
  }

  public EasyNoProviderException(String msg, Throwable parent) {
    super(msg, parent);
  }
}
