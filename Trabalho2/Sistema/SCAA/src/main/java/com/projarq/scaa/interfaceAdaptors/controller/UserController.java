package com.projarq.scaa.interfaceAdaptors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.projarq.scaa.interfaceAdaptors.useCases.IUserUseCases;

@RestController
public class UserController {
    private IUserUseCases userService;

    @Autowired
    public UserController(IUserUseCases userService) {
        this.userService = userService;
    }
}
