package org.learn2pro.codeplayground.rpc.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.learn2pro.codeplayground.rpc.core.abnormal.EasyException;
import org.learn2pro.codeplayground.rpc.core.abnormal.EasyInvokeException;
import org.springframework.util.ReflectionUtils;

public class LocalInvocationWrapper extends InvocationProxy implements InvocationHandler {

  public LocalInvocationWrapper(String targetName, Class<?> target) {
    super(targetName, target);
  }

  public LocalInvocationWrapper() {
  }

  /**
   * invoke the rpc method from local
   *
   * @param proxy  the proxy rpc provider
   * @param method the proxy rpc method
   * @param args   the input arguments
   * @return return the local invocation result
   * @throws Throwable io or inner exception
   */
  public Object invoke(Object proxy, Method method, Object[] args) throws EasyException {
    try {
      Object provider = ProviderRepository.getInstance().getByName(getTargetName());
      return ReflectionUtils.invokeMethod(method, provider, args);
    } catch (Exception e) {
      throw new EasyInvokeException(
          "invoke failed,in class:" + proxy.getClass().getSimpleName() + ",method:" + method
              .getName() + ",args:" + Arrays.toString(args), e);
    }
  }


}
