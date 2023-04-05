package com.itnan.server.service;

/**
 * 用户登录服务
 *
 * @author itnan
 * @date 2023/03/28
 */
public interface UserService {

    /**
     * 登录
     *
     * @param username     用户名
     * @param password 密码
     * @return boolean
     */
    boolean login(String username,String password);
}
