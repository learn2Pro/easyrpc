package org.learn2pro.codeplayground.rpc.core.abnormal;

import org.learn2pro.codeplayground.rpc.core.abnormal.EasyException;

public class EasyInvokeException extends EasyException {

  public EasyInvokeException(String msg, Throwable parent) {
    super(msg, parent);
  }
}
