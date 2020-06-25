package org.learn2pro.codeplayground.rpc.client;

import java.util.concurrent.Future;
import org.learn2pro.codeplayground.rpc.model.RpcRequest;
import org.learn2pro.codeplayground.rpc.model.RpcResponse;

/**
 * name:org.learn2pro.codeplayground.rpc.client.ConsumerMsgPool author:tderong date:2020/6/25
 */
public interface ConsumerMsgPool {

  /**
   * fetch the specified response of request id
   *
   * @param requestId the request id
   * @return the response
   */
  RpcResponse fetch(String requestId);

  /**
   * send requst to server
   *
   * @param request the request info
   * @return the future of response
   */
  Future<?> send(RpcRequest request);
}
