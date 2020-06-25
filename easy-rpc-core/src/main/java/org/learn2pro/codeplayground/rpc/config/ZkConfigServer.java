package org.learn2pro.codeplayground.rpc.config;

import org.learn2pro.codeplayground.rpc.server.RemoteAddr;

/**
 * name:org.learn2pro.codeplayground.rpc.config.LocalConfigServer author:tderong date:2020/6/25
 */
public class ZkConfigServer implements RpcConfigServer {

  @Override
  public RemoteAddr sense(String service) {
    return null;
  }

  @Override
  public void register(RemoteAddr addr, String service, Class<?> klass) {
  }

}
