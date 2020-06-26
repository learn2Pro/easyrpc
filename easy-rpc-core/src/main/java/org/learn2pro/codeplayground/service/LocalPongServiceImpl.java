package org.learn2pro.codeplayground.service;

import org.learn2pro.codeplayground.rpc.core.ann.Provider;
import org.springframework.stereotype.Service;

/**
 * @PACKAGE: org.learn2pro.codeplayground.service
 * @author: Dell
 * @DATE: 2020/6/23
 */
@Provider("localPongService")
@Service("localPongService")
public class LocalPongServiceImpl implements PongService {

  @Override
  public String say() {
    System.out.println("say local rpc hello!");
    return "say local rpc hello!";
  }
}
