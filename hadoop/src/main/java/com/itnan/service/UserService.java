package com.itnan.service;

import com.itnan.domain.User;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface UserService {


    public boolean login(String username, String password);
}
