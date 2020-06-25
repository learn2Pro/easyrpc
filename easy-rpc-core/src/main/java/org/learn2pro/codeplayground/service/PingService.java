package org.learn2pro.codeplayground.service;

import org.learn2pro.codeplayground.rpc.core.ann.Consumer;

/**
 * @PACKAGE: org.learn2pro.codeplayground.service
 * @author: Dell
 * @DATE: 2020/6/23
 */
public class PingService {

  @Consumer("remotePongService")
  private PongService pongService;

  public void ping() {
    System.out.println("ping start:");
    String ans = pongService.say();
    System.out.println("pong service return:" + ans);
  }

}
