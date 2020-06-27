package org.learn2pro.codeplayground.rpc.model;

/**
 * name:org.learn2pro.codeplayground.rpc.model.RpcCode author:tderong date:2020/6/25
 */
public enum RpcCode {
  /**
   * SUCCESS
   */
  SUCCESS(200, "succeed"),
  INTERNAL_ERROR(300, "internal error"),
  REQUEST_ERROR(301, "request parameter error"),
  NETWORK_ERROR(400, "network error"),
  SERVER_ERROR(500, "server error");
  /**
   * code for rpc
   */
  private int code;
  /**
   * msg for error
   */
  private String msg;

  RpcCode(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public static RpcCode of(int code0) {
    for (RpcCode current : RpcCode.values()) {
      if (current.getCode() == code0) {
        return current;
      }
    }
    throw new RuntimeException("not contain this code" + code0 + " in system!");
  }

  public int getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
}
