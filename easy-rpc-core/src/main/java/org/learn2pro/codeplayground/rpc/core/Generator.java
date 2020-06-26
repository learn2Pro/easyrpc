package org.learn2pro.codeplayground.rpc.core;

/**
 * @NAME :org.learn2pro.codeplayground.rpc.core.Generator
 * @AUTHOR :tderong
 * @DATE :2020/6/26
 */
public interface Generator {

  /**
   * generate the request id
   *
   * @return the id
   */
  String generateId();

}
