package org.learn2pro.codeplayground.service;

import org.learn2pro.codeplayground.rpc.core.ann.Provider;
import org.springframework.stereotype.Service;

/**
 * @NAME :org.learn2pro.codeplayground.service.UserServiceImpl
 * @AUTHOR :tderong
 * @DATE :2020/6/27
 */
@Service
@Provider("userService")
public class UserServiceImpl implements UserService {

  @Override
  public UserModel query(Integer id) {
    return UserModel.fake();
  }
}
