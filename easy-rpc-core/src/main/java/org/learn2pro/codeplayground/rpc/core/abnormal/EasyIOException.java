package org.learn2pro.codeplayground.rpc.core.abnormal;

import org.learn2pro.codeplayground.rpc.core.abnormal.EasyException;

public class EasyIOException extends EasyException {

  public EasyIOException(String msg) {
    super(msg);
  }

  /**
   * constructor with io exception
   *
   * @param msg    the exception message
   * @param parent the parent exception
   */
  public EasyIOException(String msg, Throwable parent) {
    super(msg, parent);
  }
}
