package org.learn2pro.codeplayground.service;

import org.learn2pro.codeplayground.rpc.core.ann.Consumer;
import org.learn2pro.codeplayground.rpc.core.enumerate.ProviderType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @PACKAGE: org.learn2pro.codeplayground.service
 * @author: Dell
 * @DATE: 2020/6/23
 */
public class PingService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PingService.class);
  @Consumer(value = "remotePongService", typo = ProviderType.REMOTE)
  private PongService pongService;
  @Consumer(value = "userService", typo = ProviderType.REMOTE)
  private UserService userService;

  public void ping() {
    long start = System.currentTimeMillis();
    LOGGER.info("ping start:{}", start);
    String ans = pongService.say();
    LOGGER.info("pong service return:{},cost:{}", ans, System.currentTimeMillis() - start);
  }

  public void query() {
    long start = System.currentTimeMillis();
    LOGGER.info("query start:{}", start);
    UserModel userModel = userService.query(1);
    LOGGER.info("query service return:{},cost:{}", userModel, System.currentTimeMillis() - start);
  }

}
