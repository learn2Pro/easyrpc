package org.learn2pro.codeplayground.rpc.core;

import java.util.UUID;
import org.learn2pro.codeplayground.rpc.core.ann.Gen;

/**
 * @NAME :org.learn2pro.codeplayground.rpc.core.UUIDGenerator
 * @AUTHOR :tderong
 * @DATE :2020/6/26
 */
@Gen("UUID")
public class UUIDGenerator implements Generator {

  @Override
  public String generateId() {
    return UUID.randomUUID().toString();
  }
}
