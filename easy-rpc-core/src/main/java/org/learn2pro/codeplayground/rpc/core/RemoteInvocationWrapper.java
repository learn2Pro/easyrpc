package org.learn2pro.codeplayground.rpc.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.learn2pro.codeplayground.rpc.client.AsyncRpcMsgPool;
import org.learn2pro.codeplayground.rpc.core.abnormal.EasyException;
import org.learn2pro.codeplayground.rpc.model.RpcCode;
import org.learn2pro.codeplayground.rpc.model.RpcRequest;
import org.learn2pro.codeplayground.rpc.model.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @PACKAGE: org.learn2pro.codeplaygroud.rpc.core
 * @author: Dell
 * @DATE: 2020/6/23
 */
public class RemoteInvocationWrapper extends InvocationProxy implements InvocationHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(RemoteInvocationWrapper.class);

  public RemoteInvocationWrapper(String targetName, Class<?> target) {
    super(targetName, target);
  }

  public RemoteInvocationWrapper() {
  }

  /**
   * invoke the rpc method from [remote]
   *
   * @param proxy  the proxy rpc provider
   * @param method the proxy rpc method
   * @param args   the input arguments
   * @return return the [remote] invocation result
   * @throws Throwable io or inner excetion
   */
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    String sessionId = GeneratorRegistry.getInstance("UUID").generateId();
    int size = args == null ? 0 : args.length;
    Class<?>[] parameterTypes = new Class[size];
    for (int i = 0; i < size; i++) {
      parameterTypes[i] = args[i].getClass();
    }

    RpcRequest request = new RpcRequest(sessionId, getTargetName(), getTarget(), method.getName(),
        parameterTypes, args);
    RpcResponse response = AsyncRpcMsgPool.getInstance().send(request)
        .get(3000, TimeUnit.MILLISECONDS);
    if (response.getRpcCode() == RpcCode.SUCCESS) {
      return response.getData();
    } else {
      throw new EasyException(response.getRpcCode().getMsg());
    }
  }
}
