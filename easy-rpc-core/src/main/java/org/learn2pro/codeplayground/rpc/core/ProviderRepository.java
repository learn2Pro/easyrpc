package org.learn2pro.codeplayground.rpc.core;

import com.google.common.base.Preconditions;
import java.lang.reflect.Method;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.learn2pro.codeplayground.rpc.config.RpcConfigServer;
import org.learn2pro.codeplayground.rpc.core.ann.Provider;
import org.learn2pro.codeplayground.rpc.model.RpcCode;
import org.learn2pro.codeplayground.rpc.model.RpcRequest;
import org.learn2pro.codeplayground.rpc.model.RpcResponse;
import org.learn2pro.codeplayground.rpc.server.RemoteAddr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

/**
 * the provider repo in [remote and local]
 *
 * @PACKAGE: org.learn2pro.codeplaygroud.rpc.core
 * @author: Dell
 * @DATE: 2020/6/23
 */
public class ProviderRepository implements Repository, ApplicationContextAware, InitializingBean {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProviderRepository.class);
  private static final ProviderRepository INSTANCE = new ProviderRepository();
  /**
   * the app context in spring
   */
  private ApplicationContext app;
  @Autowired
  private RpcConfigServer rpcConfigServer;

  public static ProviderRepository getInstance() {
    return INSTANCE;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getByName(String name) throws Exception {
    Preconditions.checkArgument(app != null, "app context is empty,pls check!");
    return (T) app.getBean(name);
  }

  /**
   * invoke the rpc request
   *
   * @param request the request body
   * @return the response
   */
  public RpcResponse invoke(RpcRequest request) {
    if (null == request.getServiceId() || null == request.getSessionId()) {
      return RpcResponse.parameterError(request.getSessionId());
    }
    Object instance = findInstance(request);
    Method m = ReflectionUtils
        .findMethod(instance.getClass(), request.getMethod(), request.getArgKlazz());
    if (m == null) {
      return RpcResponse.parameterError(request.getSessionId());
    }
    Object ans = ReflectionUtils.invokeMethod(m, instance, request.getArgs());
    LOGGER.info("provider:{}", request.getSessionId());
    return new RpcResponse(request.getSessionId(), RpcCode.SUCCESS, ans);
  }

  /**
   * find the provider instance in spring
   *
   * @param request the remote request
   * @return the instance to call
   */
  private Object findInstance(RpcRequest request) {
    Object instance;
    try {
      instance = app.getBean(request.getServiceId());
    } catch (BeansException e) {
      instance = app.getBean(request.getKlass());
    }
    return instance;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    app = applicationContext;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    registerProvider(this.app);
  }

  private void registerProvider(ApplicationContext app) {
    for (Map.Entry<String, Object> entry : app.getBeansWithAnnotation(Provider.class).entrySet()) {
      Class<?> klass = entry.getValue().getClass();
      Provider ann = klass.getAnnotation(Provider.class);
      String name =
          StringUtils.isEmpty(ann.value()) ? StringUtils.uncapitalize(klass.getSimpleName())
              : ann.value();
      rpcConfigServer.register(RemoteAddr.local(), name, klass);
    }
  }

  public void setRpcConfigServer(RpcConfigServer rpcConfigServer) {
    this.rpcConfigServer = rpcConfigServer;
  }
}
