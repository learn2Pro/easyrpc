package org.learn2pro.codeplayground.service;

import org.learn2pro.codeplayground.rpc.core.ann.Provider;
import org.springframework.stereotype.Service;

/**
 * @PACKAGE: org.learn2pro.codeplayground.service
 * @author: Dell
 * @DATE: 2020/6/23
 */
@Provider("remotePongService")
@Service("remotePongService")
public class RemotePongServiceImpl implements PongService {

  @Override
  public String say() {
    System.out.println("say remote rpc hello!");
    return "say remote rpc hello!";
  }
}
