package org.learn2pro.codeplayground.rpc.codec;

import org.learn2pro.codeplayground.rpc.model.RpcSerModel;

/**
 * name:org.learn2pro.codeplayground.rpc.codec.CodecProcessor author:tderong date:2020/6/25
 */
public interface CodecProcessor<T extends RpcSerModel> {

  /**
   * encode data
   *
   * @param data data entity
   * @return byte array
   */
  byte[] encode(T data);

  /**
   * decode bytes to entity
   *
   * @param bytes the byte array
   * @return the entity
   */
  T decode(byte[] bytes);
}
