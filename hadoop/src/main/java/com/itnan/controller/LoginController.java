package com.itnan.controller;

import com.itnan.domain.User;
import com.itnan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;


    @PostMapping
    private Result login(@RequestBody User user){
        boolean flag = userService.login(user.getUserName(), user.getPassword());
        return  new Result(flag  ? Code.GET_OK:Code.GET_ERR,flag);
    }
}
