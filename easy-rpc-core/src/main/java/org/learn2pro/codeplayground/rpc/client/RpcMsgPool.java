package org.learn2pro.codeplayground.rpc.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.learn2pro.codeplayground.rpc.model.RpcRequest;
import org.learn2pro.codeplayground.rpc.model.RpcResponse;

/**
 * @author :tderong
 * @name :org.learn2pro.codeplayground.rpc.client.ConsumerMsgPool
 * @date :2020/6/25
 */
public interface RpcMsgPool {

  ExecutorService PROCESSORS = new ThreadPoolExecutor(5, 5, 0,
      TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>(500));

  /**
   * put response into pool
   *
   * @param response the response
   */
  void answer(RpcResponse response);

  /**
   * send requst to server
   *
   * @param request the request info
   * @return the future of response
   */
  Future<RpcResponse> send(RpcRequest request);
}
