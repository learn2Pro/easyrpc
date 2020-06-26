package org.learn2pro.codeplayground.rpc.model;

/**
 * name:org.learn2pro.codeplayground.rpc.model.RpcResponse author:tderong date:2020/6/25
 */
public class RpcResponse implements RpcSerModel {

  /**
   * the request id
   */
  private String id;
  /**
   * the request status
   */
  private RpcCode rpcCode;
  /**
   * the response data
   */
  private Object data;

  public RpcResponse() {
  }

  public RpcResponse(String id, RpcCode rpcCode, Object data) {
    this.id = id;
    this.rpcCode = rpcCode;
    this.data = data;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public RpcCode getRpcCode() {
    return rpcCode;
  }

  public void setRpcCode(RpcCode rpcCode) {
    this.rpcCode = rpcCode;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public static RpcResponse parameterError(String sessionId) {
    return new RpcResponse(sessionId, RpcCode.REQUEST_ERROR, null);
  }

  public static RpcResponse networkError(String sessionId) {
    return new RpcResponse(sessionId, RpcCode.NETWORK_ERROR, null);
  }
}
