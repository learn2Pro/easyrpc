package org.learn2pro.codeplayground.rpc.config;

import org.learn2pro.codeplayground.rpc.core.ann.CodecType;
import org.learn2pro.codeplayground.rpc.server.RemoteAddr;

/**
 * name:org.learn2pro.codeplayground.rpc.config.RpcConfigServer author:tderong date:2020/6/24
 */
public interface RpcConfigServer {

  /**
   * sense the remote addr
   *
   * @param service the rpc interface
   * @return return the remote addr
   */
  RemoteAddr sense(String service) throws Throwable;

  /**
   * register the service in config server
   *
   * @param addr    the server address
   * @param service the service
   */
  void register(RemoteAddr addr, String service, Class<?> klass);

  /**
   * current codec type
   *
   * @return codec type
   */
  default CodecType fetchCodec() {
    return CodecType.JSON;
  }
}
