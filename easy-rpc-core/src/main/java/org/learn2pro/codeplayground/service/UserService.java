package org.learn2pro.codeplayground.service;

/**
 * @NAME :org.learn2pro.codeplayground.service.UserService
 * @AUTHOR :tderong
 * @DATE :2020/6/27
 */
public interface UserService {

  /**
   * query by id
   *
   * @param id the user id
   * @return user model
   */
  UserModel query(Integer id);

}
